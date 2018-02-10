package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.folder.CreatedFolderResponse;
import urchin.model.folder.CreatedFolder;
import urchin.model.folder.FolderId;
import urchin.model.folder.ImmutableCreatedFolder;

import static org.assertj.core.api.Assertions.assertThat;
import static urchin.controller.mapper.CreatedFolderMapper.mapToCreatedFolderResponse;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

public class CreatedFolderMapperTest {

    @Test
    public void mappedToResponse() {
        CreatedFolder createdFolder = ImmutableCreatedFolder.builder()
                .folderId(FolderId.of(1))
                .passphrase(generateEcryptfsPassphrase())
                .build();

        CreatedFolderResponse createdFolderResponse = mapToCreatedFolderResponse(createdFolder);

        assertThat(createdFolderResponse.getId()).isEqualTo(createdFolder.getFolderId().getValue());
        assertThat(createdFolderResponse.getPassphrase()).isEqualTo(createdFolder.getPassphrase().getValue());
    }

}