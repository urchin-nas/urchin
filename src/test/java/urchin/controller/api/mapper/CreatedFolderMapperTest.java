package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.folder.CreatedFolderDto;
import urchin.model.folder.CreatedFolder;
import urchin.model.folder.FolderId;
import urchin.model.folder.ImmutableCreatedFolder;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.CreatedFolderMapper.mapToCreatedFolderDto;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

public class CreatedFolderMapperTest {

    @Test
    public void mappedToDto() {
        CreatedFolder createdFolder = ImmutableCreatedFolder.builder()
                .folderId(FolderId.of(1))
                .passphrase(generateEcryptfsPassphrase())
                .build();

        CreatedFolderDto createdFolderDto = mapToCreatedFolderDto(createdFolder);

        assertEquals(createdFolder.getFolderId().getValue(), createdFolderDto.getId());
        assertEquals(createdFolder.getPassphrase().getValue(), createdFolderDto.getPassphrase());
    }

}