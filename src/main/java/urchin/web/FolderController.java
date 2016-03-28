package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.api.EncryptedFolderApi;
import urchin.api.MountEncryptedFolderApi;
import urchin.api.PassphraseApi;
import urchin.api.support.ResponseMessage;
import urchin.domain.EncryptedFolder;
import urchin.domain.Passphrase;
import urchin.service.FolderService;
import urchin.util.EncryptedFolderUtil;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;

import static urchin.api.support.error.ResponseExceptionBuilder.unexpectedError;

@RestController()
@RequestMapping("folder")
public class FolderController {

    public static final String OK = "OK";
    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<PassphraseApi>> createEncryptedFolder(@Valid @RequestBody EncryptedFolderApi encryptedFolderApi) {
        try {
            Passphrase passphrase = folderService.createAndMountEncryptedFolder(Paths.get(encryptedFolderApi.getFolder()));
            return new ResponseEntity<>(new ResponseMessage<>(new PassphraseApi(passphrase.getPassphrase())), HttpStatus.OK);
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }

    @RequestMapping(value = "mount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> mountEncryptedFolder(@Valid @RequestBody MountEncryptedFolderApi mountEncryptedFolderApi) {
        EncryptedFolder encryptedFolder = EncryptedFolderUtil.getEncryptedFolder(Paths.get(mountEncryptedFolderApi.getFolder()));
        Passphrase passphrase = new Passphrase(mountEncryptedFolderApi.getPassphrase());
        try {
            folderService.mountEncryptedFolder(encryptedFolder, passphrase);
            return new ResponseEntity<>(new ResponseMessage<>(OK), HttpStatus.OK);
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }

    @RequestMapping(value = "unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> unmountEncryptedFolder(@Valid @RequestBody EncryptedFolderApi encryptedFolderApi) {
        try {
            folderService.umountEncryptedFolder(Paths.get(encryptedFolderApi.getFolder()));
            return new ResponseEntity<>(new ResponseMessage<>(OK), HttpStatus.OK);
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }
}
