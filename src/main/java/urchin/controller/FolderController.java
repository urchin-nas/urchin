package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.folder.*;
import urchin.model.folder.*;
import urchin.service.FolderService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static urchin.controller.mapper.CreatedFolderMapper.mapToCreatedFolderResponse;
import static urchin.controller.mapper.FolderMapper.mapToFolderDetailsResponse;
import static urchin.controller.mapper.FolderMapper.mapToFolderDetailsResponses;

@RestController
@RequestMapping(value = "api/folders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FolderController {

    private final FolderService folderService;

    @Autowired
    FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FolderDetailsResponse> getFolders() {
        List<FolderSettings> folders = folderService.getFolders();
        return mapToFolderDetailsResponses(folders);
    }

    @GetMapping(value = "{folderId}")
    public FolderDetailsResponse getFolder(@PathVariable int folderId) {
        FolderId fid = FolderId.of(folderId);
        return mapToFolderDetailsResponse(folderService.getFolder(fid));
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CreatedFolderResponse createEncryptedFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        CreatedFolder createdFolder = folderService.createAndMountEncryptedFolder(folder);
        return mapToCreatedFolderResponse(createdFolder);
    }

    @DeleteMapping(value = "{folderId}")
    public MessageResponse deleteEncryptedFolder(@PathVariable int folderId) {
        folderService.deleteEncryptedFolder(FolderId.of(folderId));
        return ImmutableMessageResponse.of("Folder deleted");
    }

    @PostMapping(value = "mount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse mountEncryptedFolder(@Valid @RequestBody MountEncryptedFolderRequest mountEncryptedFolderRequest) throws IOException {
        FolderId folderId = FolderId.of(mountEncryptedFolderRequest.getFolderId());
        Passphrase passphrase = ImmutablePassphrase.of(mountEncryptedFolderRequest.getPassphrase());
        folderService.mountEncryptedFolder(folderId, passphrase);
        return ImmutableMessageResponse.of("virtual folder created");
    }

    @PostMapping(value = "unmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unmountEncryptedFolder(@Valid @RequestBody UnmountEncryptedFolderRequest unmountEncryptedFolderRequest) {
        FolderId folderId = FolderId.of(unmountEncryptedFolderRequest.getFolderId());
        folderService.unmountEncryptedFolder(folderId);
        return ImmutableMessageResponse.of("encrypted folder unmounted");
    }

    @PostMapping(value = "virtual/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse setupVirtualFolder(@Valid @RequestBody VirtualFolderRequest virtualFolderRequest) {
        List<Folder> folders = virtualFolderRequest.getFolders().stream()
                .map(folder -> ImmutableFolder.of(Paths.get(folder)))
                .collect(Collectors.toList());
        VirtualFolder virtualFolder = ImmutableVirtualFolder.of(Paths.get(virtualFolderRequest.getVirtualFolder()));
        folderService.setupVirtualFolder(folders, virtualFolder);
        return ImmutableMessageResponse.of("virtual folder created");
    }

    @PostMapping(value = "virtual/unmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unmountVirtualFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.unmountFolder(folder);
        return ImmutableMessageResponse.of("virtual folder unmounted");
    }

    @PostMapping(value = "share", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse shareFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.shareFolder(folder);
        return ImmutableMessageResponse.of("folder shared");
    }

    @PostMapping(value = "unshare", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse unShareFolder(@Valid @RequestBody FolderRequest folderRequest) {
        Folder folder = ImmutableFolder.of(Paths.get(folderRequest.getFolder()));
        folderService.unshareFolder(folder);
        return ImmutableMessageResponse.of("folder unshared");
    }
}
