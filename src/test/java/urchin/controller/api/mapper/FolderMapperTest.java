package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.folder.FolderDetailsResponse;
import urchin.model.folder.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.FolderMapper.mapToFolderDetailsResponses;

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

        assertEquals(1, folderDetailsResponses.size());
        FolderDetailsResponse folderDetailsResponse = folderDetailsResponses.get(0);
        assertEquals(FOLDER_SETTINGS.getFolderId().getValue(), folderDetailsResponse.getFolderId());
        assertEquals(FOLDER_SETTINGS.getFolder().getPath().getFileName().toString(), folderDetailsResponse.getFolderName());
        assertEquals(FOLDER_SETTINGS.getFolder().toAbsolutePath(), folderDetailsResponse.getFolderPath());
    }

}