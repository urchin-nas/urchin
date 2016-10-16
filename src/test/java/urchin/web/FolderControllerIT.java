package urchin.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.EncryptedFolderApi;
import urchin.api.MountEncryptedFolderApi;
import urchin.api.PassphraseApi;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.EncryptedFolder;
import urchin.testutil.RestApplication;
import urchin.testutil.TemporaryFolderUmount;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.exists;
import static org.junit.Assert.*;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class FolderControllerIT extends RestApplication {

    private Path folder;
    private EncryptedFolder encryptedFolder;

    @Rule
    public TemporaryFolderUmount temporaryFolderUmount = new TemporaryFolderUmount();

    @Override
    protected String getPath() {
        return "/folders";
    }

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();

        folder = Paths.get(temporaryFolderUmount.getRoot() + "/test_folder");
        encryptedFolder = getEncryptedFolder(folder);
    }

    @Test
    public void createUnmountAndMountEncryptedFolder() {
        EncryptedFolderApi encryptedFolderApi = new EncryptedFolderApi();
        encryptedFolderApi.setFolder(folder.toAbsolutePath().toString());

        ResponseEntity<ResponseMessage<PassphraseApi>> createResponse = postCreateRequest(encryptedFolderApi);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody().getData().getPassphrase());
        assertNull(createResponse.getBody().getErrors());
        assertTrue(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

        ResponseEntity<ResponseMessage<String>> unmountResponse = postUnmountRequest(encryptedFolderApi);

        assertEquals(HttpStatus.OK, unmountResponse.getStatusCode());
        assertFalse(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

        MountEncryptedFolderApi mountEncryptedFolderApi = new MountEncryptedFolderApi();
        mountEncryptedFolderApi.setFolder(folder.toAbsolutePath().toString());
        mountEncryptedFolderApi.setPassphrase(createResponse.getBody().getData().getPassphrase());

        ResponseEntity<ResponseMessage<String>> mountResponse = postMountRequest(mountEncryptedFolderApi);
        assertEquals(HttpStatus.OK, mountResponse.getStatusCode());
        assertTrue(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

    }

    private ResponseEntity<ResponseMessage<PassphraseApi>> postCreateRequest(EncryptedFolderApi encryptedFolderApi) {
        return template.exchange(url + "/create", HttpMethod.POST, new HttpEntity<>(encryptedFolderApi), new ParameterizedTypeReference<ResponseMessage<PassphraseApi>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> postUnmountRequest(EncryptedFolderApi encryptedFolderApi) {
        return template.exchange(url + "/unmount", HttpMethod.POST, new HttpEntity<>(encryptedFolderApi), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> postMountRequest(MountEncryptedFolderApi mountEncryptedFolderApi) {
        return template.exchange(url + "/mount", HttpMethod.POST, new HttpEntity<>(mountEncryptedFolderApi), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }


}