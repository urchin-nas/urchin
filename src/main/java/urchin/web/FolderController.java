package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.api.support.ControllerSupport;
import urchin.api.support.ResponseMessage;
import urchin.service.FolderService;

@RestController("folder")
public class FolderController extends ControllerSupport {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(value = "setup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage<String> setupEncryptedFolder() {
        return new ResponseMessage<>("something");
    }
}
