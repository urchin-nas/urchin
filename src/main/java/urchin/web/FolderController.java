package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.api.EncryptedFolderDto;
import urchin.api.MountEncryptedFolderDto;
import urchin.api.PassphraseDto;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.Passphrase;
import urchin.domain.util.EncryptedFolderUtil;
import urchin.service.FolderService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;

import static urchin.api.support.DataResponseEntityBuilder.createOkResponse;
import static urchin.api.support.DataResponseEntityBuilder.createResponse;
import static urchin.api.support.error.ResponseExceptionBuilder.unexpectedError;

@RestController
@RequestMapping("api/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<PassphraseDto>> createEncryptedFolder(@Valid @RequestBody EncryptedFolderDto encryptedFolderDto) {
        try {
            Passphrase passphrase = folderService.createAndMountEncryptedFolder(Paths.get(encryptedFolderDto.getFolder()));
            return createResponse(new PassphraseDto(passphrase.getPassphrase()));
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }

    @RequestMapping(value = "mount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> mountEncryptedFolder(@Valid @RequestBody MountEncryptedFolderDto mountEncryptedFolderDto) {
        EncryptedFolder encryptedFolder = EncryptedFolderUtil.getEncryptedFolder(Paths.get(mountEncryptedFolderDto.getFolder()));
        Passphrase passphrase = new Passphrase(mountEncryptedFolderDto.getPassphrase());
        try {
            folderService.mountEncryptedFolder(encryptedFolder, passphrase);
            return createOkResponse();
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }

    @RequestMapping(value = "unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> unmountEncryptedFolder(@Valid @RequestBody EncryptedFolderDto encryptedFolderDto) {
        try {
            folderService.unmountEncryptedFolder(Paths.get(encryptedFolderDto.getFolder()));
            return createOkResponse();
        } catch (IOException e) {
            throw unexpectedError(e);
        }
    }
}
