package ib.project.misc;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFile2 {
	
	 public static void copyFile(String origin, String destination) throws IOException {
	        Path FROM = Paths.get(origin);
	        Path TO = Paths.get(destination);
	        //overwrite the destination file if it exists, and copy
	        // the file attributes, including the rwx permissions
	        CopyOption[] options = new CopyOption[]{
	          StandardCopyOption.REPLACE_EXISTING,
	          StandardCopyOption.COPY_ATTRIBUTES
	        }; 
	        Files.copy(FROM, TO, options);
	    }

}
