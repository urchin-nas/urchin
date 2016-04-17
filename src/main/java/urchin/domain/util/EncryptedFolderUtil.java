package urchin.domain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.model.EncryptedFolder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptedFolderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptedFolderUtil.class);
    public static final String SLASH_HIDDEN_FOLDER = "/.";
    private static final String SLASH = "/";

    public static Path getFolder(EncryptedFolder encryptedFolder) {
        String path = encryptedFolder.getPath().toAbsolutePath().toString();
        String folderPath = path.substring(0, path.lastIndexOf(SLASH_HIDDEN_FOLDER)) + SLASH + path.substring(path.lastIndexOf(SLASH_HIDDEN_FOLDER) + SLASH_HIDDEN_FOLDER.length());
        LOG.debug("Folder path {}", folderPath);
        return Paths.get(folderPath);
    }

    public static EncryptedFolder getEncryptedFolder(Path folder) {
        String path = folder.toAbsolutePath().toString();
        String encryptedFolderPath = path.substring(0, path.lastIndexOf(SLASH)) + SLASH_HIDDEN_FOLDER + path.substring(path.lastIndexOf(SLASH) + SLASH.length());
        LOG.debug("Encrypted folder path {}", encryptedFolderPath);
        return new EncryptedFolder(Paths.get(encryptedFolderPath));
    }
}
