package epntech.cbdmq.pe.util;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Component;

import static epntech.cbdmq.pe.constante.MensajesConst.FOLDER_MAX_SIZE;

@Component
public class Compress {

	private static final String PROPERTIES_FILE = "application.properties";

	private Properties properties;

	public void zip(String path) throws Exception {
		properties = new Properties();
		try (InputStream stream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties.load(stream);
		}

		String folderSizeString = properties.getProperty("pecb.folder-size");
		Long size = Long.valueOf(folderSizeString);		

		if (getFolderSize(path) <= size) {
			String sourceFile = path;
			FileOutputStream fos = new FileOutputStream(path + ".zip");
			ZipOutputStream zipOut = new ZipOutputStream(fos);

			File fileToZip = new File(sourceFile);
			zipFile(fileToZip, fileToZip.getName(), zipOut);
			zipOut.close();
			fos.close();
		} else {
			throw new IOException(FOLDER_MAX_SIZE + size + " MB");
		}
	}

	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	public long getFolderSize(String ruta) throws IOException {
		Path folder = Paths.get(ruta);
		if (!Files.isDirectory(folder)) {
			return Files.size(folder);
		}
		long size = 0;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
			for (Path file : stream) {
				size += getFolderSize(file.toString());
			}
		}
		size = size / 1000000;
		// System.out.println("Folder size: " + size + " MB");
		return size;
	}

}
