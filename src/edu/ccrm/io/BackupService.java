package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ccrm.config.AppConfig;
import edu.ccrm.util.UtilRecursion;

public class BackupService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Path backupExports() throws IOException {
        if(!Files.exists(cfg.exportDir())) return null;
        Files.createDirectories(cfg.backupDir());
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path target = cfg.backupDir().resolve("backup-"+ts);
        Files.createDirectories(target);
        try(var stream = Files.list(cfg.exportDir())){
            stream.forEach(p->{
                try { Files.copy(p, target.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING); }
                catch (IOException e) { e.printStackTrace(); }
            });
        }
        return target;
    }

    public long backupSize(Path backup) throws IOException {
        return UtilRecursion.folderSize(backup);
    }
}
