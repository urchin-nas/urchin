package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.folder.CreatedFolderResponse;
import urchin.model.folder.CreatedFolder;
import urchin.model.folder.FolderId;
import urchin.model.folder.ImmutableCreatedFolder;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.CreatedFolderMapper.mapToCreatedFolderResponse;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

public class CreatedFolderMapperTest {

    @Test
    public void mappedToResponse() {
        CreatedFolder createdFolder = ImmutableCreatedFolder.builder()
                .folderId(FolderId.of(1))
                .passphrase(generateEcryptfsPassphrase())
                .build();

        CreatedFolderResponse createdFolderResponse = mapToCreatedFolderResponse(createdFolder);

        assertEquals(createdFolder.getFolderId().getValue(), createdFolderResponse.getId());
        assertEquals(createdFolder.getPassphrase().getValue(), createdFolderResponse.getPassphrase());
    }

}