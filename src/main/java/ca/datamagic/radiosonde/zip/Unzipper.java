/**
 * 
 */
package ca.datamagic.radiosonde.zip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Greg
 *
 */
public class Unzipper {

	public Unzipper() {
	}

	@SuppressWarnings("unchecked")
	public static String unzip(String zipFileName) throws IOException {
		ZipFile zipFile = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String zipPathName = "C:/Dev/Applications/Radiosonde/src/main/resources/data";
			zipFile = new ZipFile(zipFileName);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zipFile.entries();
			if (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();				
				String outputFileName = MessageFormat.format("{0}/{1}", zipPathName, zipEntry.getName()); 
				inputStream = zipFile.getInputStream(zipEntry);
				outputStream = new FileOutputStream(outputFileName);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > 0) {
					outputStream.write(buffer, 0, bytesRead);
				}
				return outputFileName;
			}
			return null;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (zipFile != null) {
				zipFile.close();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			String filePath = unzip("C:/Dev/Applications/Radiosonde/src/main/resources/data/USM00072528-data-beg2016.txt.zip");
			System.out.println(filePath);
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
