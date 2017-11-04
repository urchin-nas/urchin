package urchin.controller.api.mapper;

import urchin.controller.api.folder.CreatedFolderDto;
import urchin.controller.api.folder.ImmutableCreatedFolderDto;
import urchin.model.folder.CreatedFolder;

public class CreatedFolderMapper {

    public static CreatedFolderDto mapToCreatedFolderDto(CreatedFolder createdFolder) {
        return ImmutableCreatedFolderDto.builder()
                .id(createdFolder.getFolderId().getValue())
                .passphrase(createdFolder.getPassphrase().getValue())
                .build();

    }
}
