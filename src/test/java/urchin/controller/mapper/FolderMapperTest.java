package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.folder.FolderDetailsResponse;
import urchin.model.folder.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static urchin.controller.mapper.FolderMapper.mapToFolderDetailsResponses;

public class FolderMapperTest {

    private static final FolderSettings FOLDER_SETTINGS = ImmutableFolderSettings.builder()
            .folderId(FolderId.of(1))
            .folder(ImmutableFolder.of(Paths.get("/some/path")))
            .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get("/some/.path")))
            .created(LocalDateTime.now())
            .isAutoMount(true)
            .build();

    @Test
    public void mappedToResponse() {
        List<FolderDetailsResponse> folderDetailsResponses = mapToFolderDetailsResponses(Collections.singletonList(FOLDER_SETTINGS));

        assertThat(folderDetailsResponses).hasSize(1);
        FolderDetailsResponse folderDetailsResponse = folderDetailsResponses.get(0);
        assertThat(folderDetailsResponse.getFolderId()).isEqualTo(FOLDER_SETTINGS.getFolderId().getValue());
        assertThat(folderDetailsResponse.getFolderName()).isEqualTo(FOLDER_SETTINGS.getFolder().getPath().getFileName().toString());
        assertThat(folderDetailsResponse.getFolderPath()).isEqualTo(FOLDER_SETTINGS.getFolder().toAbsolutePath());
    }

}