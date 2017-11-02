/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**  
 * @author dayanto
 * Based on code from here (http://stackoverflow.com/questions/740375/directories-in-a-zip-file-when-using-java-util-zip-zipoutputstream)
 * TheAndrey: Rewritten to use new IO
 */

public class Zipper
{	
	/**
	 * Packages a folder with all of its content into a zip file.
	 */
	public static void zipFolder(Path folder, Path zipFile) throws IOException, Exception {
		// handle bad input
		if(!Files.exists(folder)) {
			throw new FileNotFoundException("Folder doesn't exist: " + folder);
		}
		if(!Files.isDirectory(folder)) {
			throw new IllegalArgumentException("That's not a folder: " + folder);
		}
		
		// create directory
		Path parentDir = zipFile.getParent();
		if(parentDir != null && !Files.exists(parentDir)) {
			Files.createDirectories(parentDir);
		}
		else if(Files.exists(zipFile)) { // attempt to clear any existing file.
			if(!Files.delete(zipFile)) {
				throw new Exception("Zip Failed: Existing file can't be deleted.");
			}
		}

		// create the output stream to zip file result
		OpenOption[] options = {StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
		try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(zipFile, options))) {

			// add the folder to the zip
			addFolder("", folder, zipOut);

			// close the zip objects
			zipOut.flush();
			zipOut.close();
		}
	}

	private static void addFolder(String currentPath, Path folder, ZipOutputStream zipOut) throws IOException {
		// update the internal folder path
		currentPath = currentPath + folder.getFileName() + "/";

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
			for(Path item : stream) {
				if(Files.isDirectory(item)) {
					// recursively add subfolders
					addFolder(currentPath, item, zipOut);
				} else if(Files.isRegularFile(item)) {
					// add files to the zip
					addFileEntry(currentPath, item, zipOut);
				}
			}
		}
	}

	private static void addFileEntry(String currentPath, Path file, ZipOutputStream zipOut) throws IOException {
		// 4 MiB buffer
		byte[] buffer = new byte[4 * 1024 * 1024];

		ZipEntry entry = new ZipEntry(currentPath + file.getFileName());

		// write the entry to file
		try (InputStream in = Files.newInputStream(file, StandardOpenOption.READ)) {
			zipOut.putNextEntry(entry);

			int length;
			while((length = in.read(buffer)) > 0) {
				zipOut.write(buffer, 0, length);
			}
		}
		
		// clean up resources
		in.close();
		zipOut.closeEntry();
	}
}