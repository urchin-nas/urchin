package urchin.controller.mapper;

import urchin.controller.api.folder.CreatedFolderResponse;
import urchin.controller.api.folder.ImmutableCreatedFolderResponse;
import urchin.model.folder.CreatedFolder;

public class CreatedFolderMapper {

    public static CreatedFolderResponse mapToCreatedFolderResponse(CreatedFolder createdFolder) {
        return ImmutableCreatedFolderResponse.builder()
                .id(createdFolder.getFolderId().getValue())
                .passphrase(createdFolder.getPassphrase().getValue())
                .build();

    }
}
