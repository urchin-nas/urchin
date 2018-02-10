package urchin.controller.mapper;

import urchin.controller.api.folder.FolderDetailsResponse;
import urchin.controller.api.folder.ImmutableFolderDetailsResponse;
import urchin.model.folder.FolderSettings;

import java.util.List;
import java.util.stream.Collectors;

public class FolderMapper {
    public static List<FolderDetailsResponse> mapToFolderDetailsResponses(List<FolderSettings> folders) {
        return folders.stream()
                .map(FolderMapper::mapToFolderDetailsResponse)
                .collect(Collectors.toList());
    }

    public static FolderDetailsResponse mapToFolderDetailsResponse(FolderSettings folderSettings) {
        return ImmutableFolderDetailsResponse.builder()
                .folderId(folderSettings.getFolderId().getValue())
                .folderName(folderSettings.getFolder().getPath().getFileName().toString())
                .folderPath(folderSettings.getFolder().toAbsolutePath())
                .build();
    }
}
