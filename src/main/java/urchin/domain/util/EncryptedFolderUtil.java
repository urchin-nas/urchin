package urchin.domain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.ImmutableEncryptedFolder;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

public class EncryptedFolderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptedFolderUtil.class);
    public static final String HIDDEN_FOLDER_DELIMITER = separator + ".";
    private static final String DELIMITER = separator;

    public static Path getFolder(EncryptedFolder encryptedFolder) {
        String path = encryptedFolder.getPath().toAbsolutePath().toString();
        String folderPath = path.substring(0, path.lastIndexOf(HIDDEN_FOLDER_DELIMITER)) + DELIMITER + path.substring(path.lastIndexOf(HIDDEN_FOLDER_DELIMITER) + HIDDEN_FOLDER_DELIMITER.length());
        LOG.debug("Folder path {}", folderPath);
        return Paths.get(folderPath);
    }

    public static EncryptedFolder getEncryptedFolder(Path folder) {
        String path = folder.toAbsolutePath().toString();
        String encryptedFolderPath = path.substring(0, path.lastIndexOf(DELIMITER)) + HIDDEN_FOLDER_DELIMITER + path.substring(path.lastIndexOf(DELIMITER) + DELIMITER.length());
        LOG.debug("Encrypted folder path {}", encryptedFolderPath);
        return ImmutableEncryptedFolder.of(Paths.get(encryptedFolderPath));
    }
}
