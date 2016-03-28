package urchin.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urchin.api.CreateEncryptedFolderApi;
import urchin.api.PassphraseApi;
import urchin.api.support.ResponseMessage;
import urchin.testutil.SpringApplication;
import urchin.testutil.TemporaryFolderUmount;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.exists;
import static org.junit.Assert.*;
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;

@RunWith(SpringJUnit4ClassRunner.class)
public class FolderControllerIT extends SpringApplication {

    private String url;

    @Rule
    public TemporaryFolderUmount temporaryFolderUmount = new TemporaryFolderUmount();

    @Override
    protected String getPath() {
        return "/folder";
    }

    @Before
    public void setup() {
        url = baseUrl + getPath();
    }

    @Test
    public void createEncryptedFolderCreatesAndMountsFolderAndReturnsPassphrase() {
        CreateEncryptedFolderApi createEncryptedFolderApi = new CreateEncryptedFolderApi();
        Path folder = Paths.get(temporaryFolderUmount.getRoot() + "/test_folder");
        createEncryptedFolderApi.setFolderPath(folder.toAbsolutePath().toString());

        ResponseEntity<ResponseMessage<PassphraseApi>> response = postRequest(createEncryptedFolderApi);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getData().getPassphrase());
        assertNull(response.getBody().getErrors());
        assertTrue(exists(folder));
        assertTrue(exists(getEncryptedFolder(folder).getPath()));
    }

    private ResponseEntity<ResponseMessage<PassphraseApi>> postRequest(CreateEncryptedFolderApi createEncryptedFolderApi) {
        return template.exchange(url + "/create", HttpMethod.POST, new HttpEntity<>(createEncryptedFolderApi), new ParameterizedTypeReference<ResponseMessage<PassphraseApi>>() {
        });
    }


}