package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urchin.controller.api.ImmutableMessageDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.folder.*;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.ImmutablePassphrase;
import urchin.model.folder.Passphrase;
import urchin.service.FolderService;
import urchin.util.EncryptedFolderUtil;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static urchin.controller.api.mapper.FolderMapper.mapToFolderDetailsDtos;

@RestController
@RequestMapping("api/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FolderDetailsDto> getUsers() {
        List<FolderSettings> folders = folderService.getFolders();
        return mapToFolderDetailsDtos(folders);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PassphraseDto createEncryptedFolder(@Valid @RequestBody FolderDto folderDto) throws IOException {
        Passphrase passphrase = folderService.createAndMountEncryptedFolder(Paths.get(folderDto.getFolder()));
        return ImmutablePassphraseDto.of(passphrase.getValue());
    }

    @RequestMapping(value = "mount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto mountEncryptedFolder(@Valid @RequestBody MountEncryptedFolderDto mountEncryptedFolderDto) throws IOException {
        EncryptedFolder encryptedFolder = EncryptedFolderUtil.getEncryptedFolder(Paths.get(mountEncryptedFolderDto.getFolder()));
        Passphrase passphrase = ImmutablePassphrase.of(mountEncryptedFolderDto.getPassphrase());
        folderService.mountEncryptedFolder(encryptedFolder, passphrase);
        return ImmutableMessageDto.of("virtual folder created");
    }

    @RequestMapping(value = "unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto unmountEncryptedFolder(@Valid @RequestBody FolderDto folderDto) throws IOException {
        folderService.unmountFolder(Paths.get(folderDto.getFolder()));
        return ImmutableMessageDto.of("encrypted folder unmounted");
    }

    @RequestMapping(value = "virtual/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto setupVirtualFolder(@Valid @RequestBody VirtualFolderDto virtualFolderDto) throws IOException {
        List<Path> folders = virtualFolderDto.getFolders().stream()
                .map(folder -> Paths.get(folder))
                .collect(Collectors.toList());
        folderService.setupVirtualFolder(folders, Paths.get(virtualFolderDto.getVirtualFolder()));
        return ImmutableMessageDto.of("virtual folder created");
    }

    @RequestMapping(value = "virtual/unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto unmountVirtualFolder(@Valid @RequestBody FolderDto folderDto) throws IOException {
        folderService.unmountFolder(Paths.get(folderDto.getFolder()));
        return ImmutableMessageDto.of("virtual folder unmounted");
    }

    @RequestMapping(value = "share", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto shareFolder(@Valid @RequestBody FolderDto folderDto) {
        folderService.shareFolder(Paths.get(folderDto.getFolder()));
        return ImmutableMessageDto.of("folder shared");
    }

    @RequestMapping(value = "unshare", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto unShareFolder(@Valid @RequestBody FolderDto folderDto) {
        folderService.unshareFolder(Paths.get(folderDto.getFolder()));
        return ImmutableMessageDto.of("folder unshared");
    }
}
