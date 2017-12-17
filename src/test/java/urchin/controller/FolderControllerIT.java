package urchin.controller;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.folder.*;
import urchin.model.folder.*;
import urchin.testutil.TemporaryFolderUnmount;
import urchin.testutil.TestApplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;

public class FolderControllerIT extends TestApplication {

    private static final String FILENAME = "test_file_for_folder_service_it.txt";
    private static final String FOLDER1_NAME = "/folder1";
    private static final String FOLDER2_NAME = "/folder2";
    private static final String FOLDER_VIRTUAL_NAME = "/virtual";

    private Folder folder_1;
    private Folder folder_2;
    private EncryptedFolder encryptedFolder_1;
    private EncryptedFolder encryptedFolder_2;
    private VirtualFolder virtualFolder;

    @Rule
    public TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    @Before
    public void setup() {
        String tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();
        folder_1 = ImmutableFolder.of(Paths.get(tmpFolderPath + FOLDER1_NAME));
        folder_2 = ImmutableFolder.of(Paths.get(tmpFolderPath + FOLDER2_NAME));
        encryptedFolder_1 = getEncryptedFolder(folder_1);
        encryptedFolder_2 = getEncryptedFolder(folder_2);
        virtualFolder = ImmutableVirtualFolder.of(Paths.get(tmpFolderPath + FOLDER_VIRTUAL_NAME));
    }

    @Test
    public void createAndGetEncryptedFolder() {
        ResponseEntity<CreatedFolderResponse> createResponse = postCreateRequest(ImmutableFolderRequest.of(folder_1.toAbsolutePath()));
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        ResponseEntity<FolderDetailsResponse> folderResponse = getFolderRequest(createResponse.getBody().getId());

        assertEquals(HttpStatus.OK, folderResponse.getStatusCode());
        assertEquals(createResponse.getBody().getId(), folderResponse.getBody().getFolderId());
    }

    @Test
    public void createEncryptedFoldersAndGetFolderDetailsForAllFolders() {
        ResponseEntity<CreatedFolderResponse> createResponse_1 = postCreateRequest(ImmutableFolderRequest.of(folder_1.toAbsolutePath()));
        ResponseEntity<CreatedFolderResponse> createResponse_2 = postCreateRequest(ImmutableFolderRequest.of(folder_2.toAbsolutePath()));

        assertEquals(HttpStatus.OK, createResponse_1.getStatusCode());
        assertEquals(HttpStatus.OK, createResponse_2.getStatusCode());

        ResponseEntity<FolderDetailsResponse[]> getFoldersResponse = getFoldersRequest();

        assertEquals(HttpStatus.OK, getFoldersResponse.getStatusCode());
        assertTrue(getFoldersResponse.getBody().length > 0);
        List<String> folders = Arrays.stream(getFoldersResponse.getBody())
                .map(FolderDetailsResponse::getFolderPath)
                .collect(Collectors.toList());
        assertTrue(folders.contains(folder_1.toAbsolutePath()));
        assertTrue(folders.contains(folder_2.toAbsolutePath()));
    }

    @Test
    public void createAndDeleteEncryptedFolder() {
        ResponseEntity<CreatedFolderResponse> createResponse = postCreateRequest(ImmutableFolderRequest.of(folder_1.toAbsolutePath()));

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        Integer folderId = createResponse.getBody().getId();

        ResponseEntity<MessageResponse> deleteResponse = deleteFolderRequest(folderId);

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }

    @Test
    public void createAndMountAndUnmountEncryptedAndVirtualFoldersThatAreSharedAndUnsharedOnNetwork() throws Exception {
        FolderRequest encryptedFolderRequest = ImmutableFolderRequest.of(folder_1.toAbsolutePath());

        //1. create encrypted folder

        ResponseEntity<CreatedFolderResponse> createResponse_1 = postCreateRequest(encryptedFolderRequest);

        assertEquals(HttpStatus.OK, createResponse_1.getStatusCode());
        assertNotNull(createResponse_1.getBody().getPassphrase());
        assertTrue(folder_1.isExisting());
        assertTrue(encryptedFolder_1.isExisting());

        //2. unmount encrypted folder

        ResponseEntity<MessageResponse> unmountResponse_1 = postUnmountRequest(encryptedFolderRequest);

        assertEquals(HttpStatus.OK, unmountResponse_1.getStatusCode());
        assertFalse(folder_1.isExisting());
        assertTrue(encryptedFolder_1.isExisting());

        //3. mount encrypted folder again

        MountEncryptedFolderRequest mountEncryptedFolderRequest = ImmutableMountEncryptedFolderRequest.builder()
                .folder(folder_1.toAbsolutePath())
                .passphrase(createResponse_1.getBody().getPassphrase())
                .build();

        ResponseEntity<MessageResponse> mountResponse_1 = postMountRequest(mountEncryptedFolderRequest);
        assertEquals(HttpStatus.OK, mountResponse_1.getStatusCode());
        assertTrue(folder_1.isExisting());
        assertTrue(encryptedFolder_1.isExisting());

        //4. create 2nd encrypted folder

        ResponseEntity<CreatedFolderResponse> createResponse_2 = postCreateRequest(ImmutableFolderRequest.of(folder_2.toAbsolutePath()));
        assertEquals(HttpStatus.OK, createResponse_2.getStatusCode());
        assertTrue(folder_2.isExisting());
        assertTrue(encryptedFolder_2.isExisting());

        //5. setup virtual folder

        VirtualFolderRequest virtualFolderRequest =
                ImmutableVirtualFolderRequest.builder()
                        .folders(Arrays.asList(folder_1.toAbsolutePath(), folder_2.toAbsolutePath()))
                        .virtualFolder(virtualFolder.toAbsolutePath())
                        .build();

        ResponseEntity<MessageResponse> virtualFolderResponse = postSetupVirtualFolderRequest(virtualFolderRequest);

        assertEquals(HttpStatus.OK, virtualFolderResponse.getStatusCode());
        assertTrue(virtualFolder.isExisting());

        //.6 create file in virtual folder, which will be located in one of the encrypted folders.

        createFileInFolder(FILENAME, virtualFolder.getPath());
        assertTrue(folderContainsFile(virtualFolder.getPath(), FILENAME));
        assertTrue(folderContainsFile(folder_1.getPath(), FILENAME) || folderContainsFile(folder_2.getPath(), FILENAME));

        //7. share virtual folder

        FolderRequest shareFolderRequest = ImmutableFolderRequest.of(virtualFolder.toAbsolutePath());

        ResponseEntity<MessageResponse> shareFolderResponse = postShareFolderRequest(shareFolderRequest);
        assertEquals(HttpStatus.OK, shareFolderResponse.getStatusCode());

        SmbFile sharedFolder = getSmbFile(FOLDER_VIRTUAL_NAME);
        assertEquals(1, sharedFolder.list().length);
        assertEquals(FILENAME, sharedFolder.list()[0]);

        //8. unshare virtual folder

        ResponseEntity<MessageResponse> unshareFolderResponse = postUnshareFolderRequest(shareFolderRequest);
        assertEquals(HttpStatus.OK, unshareFolderResponse.getStatusCode());

        try {
            getSmbFile(FOLDER_VIRTUAL_NAME).list();
            fail("expected listing files in network share to fail because network share should have been removed");
        } catch (SmbException ignore) {
        }

        //9. unmount virtual folder

        ResponseEntity<MessageResponse> unmountVirtualFOlderResponse = postUnmountVirtualFolderRequest(ImmutableFolderRequest.of(virtualFolder.toAbsolutePath()));

        assertEquals(HttpStatus.OK, unmountVirtualFOlderResponse.getStatusCode());
        assertFalse(virtualFolder.isExisting());
        assertTrue(folderContainsFile(folder_1.getPath(), FILENAME) || folderContainsFile(folder_2.getPath(), FILENAME));
    }

    private ResponseEntity<FolderDetailsResponse[]> getFoldersRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), FolderDetailsResponse[].class);
    }

    private ResponseEntity<FolderDetailsResponse> getFolderRequest(int folderId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + folderId, FolderDetailsResponse.class);
    }

    private ResponseEntity<CreatedFolderResponse> postCreateRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/create", folderRequest, CreatedFolderResponse.class);
    }

    private ResponseEntity<MessageResponse> postUnmountRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/unmount", folderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postMountRequest(MountEncryptedFolderRequest mountEncryptedFolderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/mount", mountEncryptedFolderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postSetupVirtualFolderRequest(VirtualFolderRequest virtualFolderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/virtual/create", virtualFolderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postUnmountVirtualFolderRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/virtual/unmount", folderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postShareFolderRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/share", folderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> postUnshareFolderRequest(FolderRequest folderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/unshare", folderRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> deleteFolderRequest(int folderId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + folderId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageResponse>() {
        });
    }

    private Path createFileInFolder(String filename, Path folder) throws IOException {
        Path file = Paths.get(folder.toAbsolutePath().toString() + "/" + filename);
        return Files.createFile(file);
    }

    private boolean folderContainsFile(Path folder, String filename) throws IOException {
        for (Path path : Files.newDirectoryStream(folder)) {
            if (path.getFileName().toString().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    private SmbFile getSmbFile(String shareFolderName) throws MalformedURLException {
        return new SmbFile(String.format("smb://127.0.0.1/%s/", shareFolderName));
    }

}