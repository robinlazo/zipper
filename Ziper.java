package ZipCoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ziper {

	public void zipFiles(File file, ZipOutputStream zos) {
		try {

			zos.putNextEntry(new ZipEntry(file.getName()));
			
			byte bytes[] = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

			zos.write(bytes, 0, bytes.length);

			zos.closeEntry();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void zipDirectories(File file, String path, ZipOutputStream zos) throws IOException {

		for(File files : file.listFiles()) {
			if(files.isDirectory()) {
				zipDirectories(files, path + files.getName() + "/", zos);
				continue;
			}
			
			zos.putNextEntry(new ZipEntry(path + files.getName()));
			
			byte[] info = Files.readAllBytes(Paths.get(files.getAbsolutePath()));
			
			zos.write(info, 0, info.length);
			zos.closeEntry();
		}
	}

	public void zip(File files[], String name) throws FileNotFoundException, IOException{
		String fileZip = System.getProperty("user.dir") + "\\"+ name + ".zip";
	
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileZip));

		for (File file : files) {
			if (file.isDirectory()) {
				zipDirectories(file, file.getName() + "/", zos);
			} else {
				zipFiles(file, zos);
			}
		}
		
		zos.close();
	}
}
