package urchin.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.EncryptedFolderDto;
import urchin.api.MountEncryptedFolderDto;
import urchin.api.PassphraseDto;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.EncryptedFolder;
import urchin.testutil.TemporaryFolderUnmount;
import urchin.testutil.TestApplication;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.exists;
import static org.junit.Assert.*;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;

public class FolderControllerIT extends TestApplication {

    private Path folder;
    private EncryptedFolder encryptedFolder;

    @Rule
    public TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    @Before
    public void setup() {
        folder = Paths.get(temporaryFolderUnmount.getRoot() + "/test_folder");
        encryptedFolder = getEncryptedFolder(folder);
    }

    @Test
    public void createAndUnmountAndMountEncryptedFolder() {
        EncryptedFolderDto encryptedFolderDto = new EncryptedFolderDto(folder.toAbsolutePath().toString());

        ResponseEntity<ResponseMessage<PassphraseDto>> createResponse = postCreateRequest(encryptedFolderDto);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody().getData().getPassphrase());
        assertTrue(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

        ResponseEntity<ResponseMessage<String>> unmountResponse = postUnmountRequest(encryptedFolderDto);

        assertEquals(HttpStatus.OK, unmountResponse.getStatusCode());
        assertFalse(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

        MountEncryptedFolderDto mountEncryptedFolderDto = new MountEncryptedFolderDto(
                folder.toAbsolutePath().toString(),
                createResponse.getBody().getData().getPassphrase()
        );

        ResponseEntity<ResponseMessage<String>> mountResponse = postMountRequest(mountEncryptedFolderDto);
        assertEquals(HttpStatus.OK, mountResponse.getStatusCode());
        assertTrue(exists(folder));
        assertTrue(exists(encryptedFolder.getPath()));

    }

    private ResponseEntity<ResponseMessage<PassphraseDto>> postCreateRequest(EncryptedFolderDto encryptedFolderDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/create", HttpMethod.POST, new HttpEntity<>(encryptedFolderDto), new ParameterizedTypeReference<ResponseMessage<PassphraseDto>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> postUnmountRequest(EncryptedFolderDto encryptedFolderDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/unmount", HttpMethod.POST, new HttpEntity<>(encryptedFolderDto), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> postMountRequest(MountEncryptedFolderDto mountEncryptedFolderDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/mount", HttpMethod.POST, new HttpEntity<>(mountEncryptedFolderDto), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }


}