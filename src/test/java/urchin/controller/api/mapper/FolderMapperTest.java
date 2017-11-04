package urchin.controller.api.mapper;

import org.junit.Test;
import urchin.controller.api.folder.FolderDetailsDto;
import urchin.model.folder.FolderId;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.ImmutableEncryptedFolder;
import urchin.model.folder.ImmutableFolderSettings;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static urchin.controller.api.mapper.FolderMapper.mapToFolderDetailsDtos;

public class FolderMapperTest {

    private static final FolderSettings FOLDER_SETTINGS = ImmutableFolderSettings.builder()
            .folderId(FolderId.of(1))
            .folder(Paths.get("/some/path"))
            .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get("/some/.path")))
            .created(LocalDateTime.now())
            .isAutoMount(true)
            .build();

    @Test
    public void mappedToDto() {
        List<FolderDetailsDto> folderDetailsDtos = mapToFolderDetailsDtos(Collections.singletonList(FOLDER_SETTINGS));

        assertEquals(1, folderDetailsDtos.size());
        FolderDetailsDto folderDetailsDto = folderDetailsDtos.get(0);
        assertEquals(FOLDER_SETTINGS.getFolderId().getValue(), folderDetailsDto.getFolderId());
        assertEquals(FOLDER_SETTINGS.getFolder().toAbsolutePath().toString(), folderDetailsDto.getFolder());
    }

}