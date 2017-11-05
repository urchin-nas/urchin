package urchin.controller.api.mapper;

import urchin.controller.api.folder.FolderDetailsDto;
import urchin.controller.api.folder.ImmutableFolderDetailsDto;
import urchin.model.folder.FolderSettings;

import java.util.List;
import java.util.stream.Collectors;

public class FolderMapper {
    public static List<FolderDetailsDto> mapToFolderDetailsDtos(List<FolderSettings> folders) {
        return folders.stream()
                .map(FolderMapper::mapToFolderDetailsDto)
                .collect(Collectors.toList());
    }

    public static FolderDetailsDto mapToFolderDetailsDto(FolderSettings folderSettings) {
        return ImmutableFolderDetailsDto.builder()
                .folderId(folderSettings.getFolderId().getValue())
                .folderName(folderSettings.getFolder().getPath().getFileName().toString())
                .folderPath(folderSettings.getFolder().toAbsolutePath())
                .build();
    }
}
