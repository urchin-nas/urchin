package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.api.CreateEncryptedFolderApi;
import urchin.api.PassphraseApi;
import urchin.api.support.ErrorResponse;
import urchin.api.support.ResponseMessage;
import urchin.api.support.error.ResponseException;
import urchin.domain.Passphrase;
import urchin.service.FolderService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static urchin.api.support.ExceptionControllerAdvice.UNKNOWN_ERROR;

@RestController()
@RequestMapping("folder")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<PassphraseApi>> createEncryptedFolder(@Valid @RequestBody CreateEncryptedFolderApi createEncryptedFolderApi) {
        try {
            Passphrase passphrase = folderService.createAndMountEncryptedFolder(Paths.get(createEncryptedFolderApi.getFolderPath()));
            return new ResponseEntity<>(new ResponseMessage<>(new PassphraseApi(passphrase.getPassphrase())), OK);
        } catch (IOException e) {
            throw new ResponseException(INTERNAL_SERVER_ERROR, new ErrorResponse(UNKNOWN_ERROR).setMessage("see logs for more details"));
        }
    }
}
