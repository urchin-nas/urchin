package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.folder.*;
import urchin.model.folder.*;
import urchin.service.FolderService;
import urchin.util.EncryptedFolderUtil;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static urchin.controller.api.mapper.CreatedFolderMapper.mapToCreatedFolderResponse;
import static urchin.controller.api.mapper.FolderMapper.mapToFolderDetailsResponse;
import static urchin.controller.api.mapper.FolderMapper.mapToFolderDetailsResponses;

@RestController
@RequestMapping("api/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FolderDetailsResponse> getFolders() {
        List<FolderSettings> folders = folderService.getFolders();
        return mapToFolderDetailsResponses(folders);
    }

    @RequestMapping(value = "{folderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public FolderDetailsResponse getFolder(@PathVariable int folderId) {
        FolderId fid = FolderId.of(folderId);
        return mapToFolderDetailsResponse(folderService.getFolder(fid));
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CreatedFolderResponse createEncryptedFolder(@Valid @RequestBody FolderRequest folderRequest) throws IOException {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        CreatedFolder createdFolder = folderService.createAndMountEncryptedFolder(folder);
        return mapToCreatedFolderResponse(createdFolder);
    }

    @RequestMapping(value = "{folderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse deleteEncryptedFolder(@PathVariable int folderId) throws IOException {
        folderService.deleteEncryptedFolder(FolderId.of(folderId));
        return ImmutableMessageResponse.of("Folder deleted");
    }

    @RequestMapping(value = "mount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse mountEncryptedFolder(@Valid @RequestBody MountEncryptedFolderRequest mountEncryptedFolderRequest) throws IOException {
        EncryptedFolder encryptedFolder = EncryptedFolderUtil.getEncryptedFolder(ImmutableFolder.of(Paths.get(mountEncryptedFolderRequest.getFolder())));
        Passphrase passphrase = ImmutablePassphrase.of(mountEncryptedFolderRequest.getPassphrase());
        folderService.mountEncryptedFolder(encryptedFolder, passphrase);
        return ImmutableMessageResponse.of("virtual folder created");
    }

    @RequestMapping(value = "unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unmountEncryptedFolder(@Valid @RequestBody FolderRequest folderRequest) throws IOException {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.unmountFolder(folder);
        return ImmutableMessageResponse.of("encrypted folder unmounted");
    }

    @RequestMapping(value = "virtual/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse setupVirtualFolder(@Valid @RequestBody VirtualFolderRequest virtualFolderRequest) throws IOException {
        List<Folder> folders = virtualFolderRequest.getFolders().stream()
                .map(folder -> ImmutableFolder.of(Paths.get(folder)))
                .collect(Collectors.toList());
        VirtualFolder virtualFolder = ImmutableVirtualFolder.of(Paths.get(virtualFolderRequest.getVirtualFolder()));
        folderService.setupVirtualFolder(folders, virtualFolder);
        return ImmutableMessageResponse.of("virtual folder created");
    }

    @RequestMapping(value = "virtual/unmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unmountVirtualFolder(@Valid @RequestBody FolderRequest folderRequest) throws IOException {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.unmountFolder(folder);
        return ImmutableMessageResponse.of("virtual folder unmounted");
    }

    @RequestMapping(value = "share", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse shareFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.shareFolder(folder);
        return ImmutableMessageResponse.of("folder shared");
    }

    @RequestMapping(value = "unshare", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unShareFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.unshareFolder(folder);
        return ImmutableMessageResponse.of("folder unshared");
    }
}
