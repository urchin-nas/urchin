package urchin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.Folder;
import urchin.model.folder.ImmutableEncryptedFolder;
import urchin.model.folder.ImmutableFolder;

import java.nio.file.Paths;

import static java.io.File.separator;

public class EncryptedFolderUtil {

    private static final Logger log = LoggerFactory.getLogger(EncryptedFolderUtil.class);
    public static final String HIDDEN_FOLDER_DELIMITER = separator + ".";
    private static final String DELIMITER = separator;

    public static Folder getFolder(EncryptedFolder encryptedFolder) {
        String path = encryptedFolder.toAbsolutePath();
        String folderPath = path.substring(0, path.lastIndexOf(HIDDEN_FOLDER_DELIMITER)) + DELIMITER + path.substring(path.lastIndexOf(HIDDEN_FOLDER_DELIMITER) + HIDDEN_FOLDER_DELIMITER.length());
        log.debug("Folder path {}", folderPath);
        return ImmutableFolder.of(Paths.get(folderPath));
    }

    public static EncryptedFolder getEncryptedFolder(Folder folder) {
        String path = folder.toAbsolutePath();
        String encryptedFolderPath = path.substring(0, path.lastIndexOf(DELIMITER)) + HIDDEN_FOLDER_DELIMITER + path.substring(path.lastIndexOf(DELIMITER) + DELIMITER.length());
        log.debug("Encrypted folder path {}", encryptedFolderPath);
        return ImmutableEncryptedFolder.of(Paths.get(encryptedFolderPath));
    }
}
