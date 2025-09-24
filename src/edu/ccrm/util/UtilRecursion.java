package edu.ccrm.util;

import java.nio.file.*;
import java.io.IOException;

public class UtilRecursion {
    public static long folderSize(Path p) throws IOException {
        if(Files.isRegularFile(p)) return Files.size(p);
        long total = 0;
        try(var ds = Files.newDirectoryStream(p)){
            for(Path c: ds){
                total += folderSize(c);
            }
        }
        return total;
    }
}
