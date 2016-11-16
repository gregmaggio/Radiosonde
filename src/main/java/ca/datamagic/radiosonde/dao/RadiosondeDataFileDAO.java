/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Greg
 *
 */
public class RadiosondeDataFileDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(RadiosondeDataFileDAO.class);
	
	public RadiosondeDataFileDAO() {
	}

	@SuppressWarnings("unchecked")
	public String getDataFile(String zipFileName) throws IOException {
		ZipFile zipFile = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String zipPathName = getDataPath();
			zipFile = new ZipFile(zipFileName);
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zipFile.entries();
			if (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();				
				String outputFileName = MessageFormat.format("{0}/{1}", zipPathName, zipEntry.getName());
				_logger.debug("outputFileName: " + outputFileName);
				
				FileTime zipFileLastModified = Files.getLastModifiedTime(Paths.get(zipFileName));
				File outputFile = new File(outputFileName);
				if (outputFile.exists()) {
					FileTime currentLastModified = Files.getLastModifiedTime(Paths.get(outputFileName));
					if (currentLastModified.toMillis() >= zipFileLastModified.toMillis()) {
						_logger.debug("Returning current data file");
						return outputFileName;
					}
				}
				
				inputStream = zipFile.getInputStream(zipEntry);
				outputStream = new FileOutputStream(outputFileName);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > 0) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				Files.setLastModifiedTime(Paths.get(outputFileName), zipFileLastModified);
				
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
	
	public static void main(String [] args) {
		try {
			String stationId = "USM00072528";
			RadiosondeZipFileDAO zipFileDAO = new RadiosondeZipFileDAO();
			String zipFile = zipFileDAO.getZipFile(stationId);
			System.out.println(zipFile);
			
			RadiosondeDataFileDAO dao = new RadiosondeDataFileDAO();
			String dataFile = dao.getDataFile(zipFile);
			System.out.println(dataFile);
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
