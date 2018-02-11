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
import urchin.controller.api.ErrorCode;
import urchin.controller.api.ErrorResponse;
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
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<FolderDetailsResponse> folderResponse = getFolderRequest(createResponse.getBody().getId());

        assertThat(folderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(folderResponse.getBody().getFolderId()).isEqualTo(createResponse.getBody().getId());
    }

    @Test
    public void createEncryptedFoldersAndGetFolderDetailsForAllFolders() {
        ResponseEntity<CreatedFolderResponse> createResponse_1 = postCreateRequest(ImmutableFolderRequest.of(folder_1.toAbsolutePath()));
        ResponseEntity<CreatedFolderResponse> createResponse_2 = postCreateRequest(ImmutableFolderRequest.of(folder_2.toAbsolutePath()));

        assertThat(createResponse_1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse_2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<FolderDetailsResponse[]> getFoldersResponse = getFoldersRequest();

        assertThat(getFoldersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getFoldersResponse.getBody().length > 0).isTrue();
        List<String> folders = Arrays.stream(getFoldersResponse.getBody())
                .map(FolderDetailsResponse::getFolderPath)
                .collect(Collectors.toList());
        assertThat(folders.contains(folder_1.toAbsolutePath())).isTrue();
        assertThat(folders.contains(folder_2.toAbsolutePath())).isTrue();
    }

    @Test
    public void createAndDeleteEncryptedFolder() {
        ResponseEntity<CreatedFolderResponse> createResponse = postCreateRequest(ImmutableFolderRequest.of(folder_1.toAbsolutePath()));

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Integer folderId = createResponse.getBody().getId();

        ResponseEntity<MessageResponse> deleteResponse = deleteFolderRequest(folderId);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createAndMountEncryptedAndVirtualFoldersThatAreSharedAndUnsharedOnNetworkAndUnmounted() throws Exception {
        FolderRequest encryptedFolderRequest = ImmutableFolderRequest.of(folder_1.toAbsolutePath());

        // create encrypted folder

        ResponseEntity<CreatedFolderResponse> createResponse_1 = postCreateRequest(encryptedFolderRequest);

        assertThat(createResponse_1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse_1.getBody().getPassphrase()).isNotNull();
        assertThat(createResponse_1.getBody().getId()).isGreaterThan(0);
        assertThat(folder_1.isExisting()).isTrue();
        assertThat(encryptedFolder_1.isExisting()).isTrue();

        // mount encrypted folder

        MountEncryptedFolderRequest mountEncryptedFolderRequest_1 = ImmutableMountEncryptedFolderRequest.builder()
                .folderId(createResponse_1.getBody().getId())
                .passphrase(createResponse_1.getBody().getPassphrase())
                .build();

        ResponseEntity<MessageResponse> mountResponse_1 = postMountRequest(mountEncryptedFolderRequest_1);
        assertThat(mountResponse_1.getStatusCode()).isEqualTo(HttpStatus.OK);

        // create 2nd encrypted folder

        ResponseEntity<CreatedFolderResponse> createResponse_2 = postCreateRequest(ImmutableFolderRequest.of(folder_2.toAbsolutePath()));
        assertThat(createResponse_2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(folder_2.isExisting()).isTrue();
        assertThat(encryptedFolder_2.isExisting()).isTrue();


        // mount 2nd encrypted folder

        MountEncryptedFolderRequest mountEncryptedFolderRequest_2 = ImmutableMountEncryptedFolderRequest.builder()
                .folderId(createResponse_1.getBody().getId())
                .passphrase(createResponse_1.getBody().getPassphrase())
                .build();

        ResponseEntity<MessageResponse> mountResponse_2 = postMountRequest(mountEncryptedFolderRequest_2);
        assertThat(mountResponse_2.getStatusCode()).isEqualTo(HttpStatus.OK);

        // setup virtual folder

        VirtualFolderRequest virtualFolderRequest =
                ImmutableVirtualFolderRequest.builder()
                        .folders(Arrays.asList(folder_1.toAbsolutePath(), folder_2.toAbsolutePath()))
                        .virtualFolder(virtualFolder.toAbsolutePath())
                        .build();

        ResponseEntity<MessageResponse> virtualFolderResponse = postSetupVirtualFolderRequest(virtualFolderRequest);

        assertThat(virtualFolderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(virtualFolder.isExisting()).isTrue();

        // create file in virtual folder, which will be located in one of the encrypted folders.

        createFileInFolder(FILENAME, virtualFolder.getPath());
        assertThat(folderContainsFile(virtualFolder.getPath(), FILENAME)).isTrue();
        assertThat(folderContainsFile(folder_1.getPath(), FILENAME) || folderContainsFile(folder_2.getPath(), FILENAME)).isTrue();

        // share virtual folder

        FolderRequest shareFolderRequest = ImmutableFolderRequest.of(virtualFolder.toAbsolutePath());

        ResponseEntity<MessageResponse> shareFolderResponse = postShareFolderRequest(shareFolderRequest);
        assertThat(shareFolderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        SmbFile sharedFolder = getSmbFile(FOLDER_VIRTUAL_NAME);
        assertThat(sharedFolder.list().length).isEqualTo(1);
        assertThat(sharedFolder.list()[0]).isEqualTo(FILENAME);

        // unshare virtual folder

        ResponseEntity<MessageResponse> unshareFolderResponse = postUnshareFolderRequest(shareFolderRequest);
        assertThat(unshareFolderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            getSmbFile(FOLDER_VIRTUAL_NAME).list();
            fail("expected listing files in network share to fail because network share should have been removed");
        } catch (SmbException ignore) {
        }

        // unmount virtual folder

        ResponseEntity<MessageResponse> unmountVirtualFolderResponse = postUnmountVirtualFolderRequest(ImmutableFolderRequest.of(virtualFolder.toAbsolutePath()));

        assertThat(unmountVirtualFolderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(folderContainsFile(folder_1.getPath(), FILENAME) || folderContainsFile(folder_2.getPath(), FILENAME)).isTrue();

        // unmount encrypted folder

        UnmountEncryptedFolderRequest unmountEncryptedFolderRequest = ImmutableUnmountEncryptedFolderRequest.builder()
                .folderId(createResponse_1.getBody().getId())
                .build();

        ResponseEntity<MessageResponse> unmountEncryptedFolderResponse = postUnmountRequest(unmountEncryptedFolderRequest);

        assertThat(unmountEncryptedFolderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // verify folders still exist

        assertThat(folder_1.isExisting()).isTrue();
        assertThat(encryptedFolder_1.isExisting()).isTrue();
        assertThat(folder_2.isExisting()).isTrue();
        assertThat(encryptedFolder_2.isExisting()).isTrue();
    }

    @Test
    public void addFolderWithEmptyRequestReturnsErrorResponse() {
        FolderRequest emptyFolderRequest = ImmutableFolderRequest.builder().build();

        ResponseEntity<ErrorResponse> response = testRestTemplate.postForEntity(discoverControllerPath() + "/create", emptyFolderRequest, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.VALIDATION_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(ControllerAdvice.VALIDATION_ERROR_MESSAGE);
        Map<String, List<String>> fieldErrors = errorResponse.getFieldErrors();
        assertThat(fieldErrors).hasSize(1);
        assertThat(fieldErrors).containsKeys("folder");
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

    private ResponseEntity<MessageResponse> postUnmountRequest(UnmountEncryptedFolderRequest unmountEncryptedFolderRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/unmount", unmountEncryptedFolderRequest, MessageResponse.class);
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