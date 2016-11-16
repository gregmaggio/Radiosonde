/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Greg
 *
 */
public class RadiosondeZipFileDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(RadiosondeZipFileDAO.class);
	private static SimpleDateFormat _lastModifiedFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	
	public RadiosondeZipFileDAO() {
	}

	public String getZipFile(String stationId) throws IOException, ParseException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String fileName = MessageFormat.format("{0}-data-beg{1}.txt.zip", stationId, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			String outputFileName = MessageFormat.format("{0}/{1}", getDataPath(), fileName);
			_logger.debug("outputFileName: " + outputFileName);
			
			String urlSpec = MessageFormat.format("http://www1.ncdc.noaa.gov/pub/data/igra/data/data-y2d/{0}", fileName);
			_logger.debug("urlSpec: " + urlSpec);
			
			URL url = new URL(urlSpec);
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			_logger.debug("responseCode: " + responseCode);
			
			if (responseCode == 404)
				return null;
			
			boolean redirect = false;
			if (responseCode != HttpURLConnection.HTTP_OK) {
				if ((responseCode == HttpURLConnection.HTTP_MOVED_TEMP) || (responseCode == HttpURLConnection.HTTP_MOVED_PERM) || (responseCode == HttpURLConnection.HTTP_SEE_OTHER))
				redirect = true;
			}
			
			if (redirect) {
				// get redirect url from "location" header field
				String location = connection.getHeaderField("Location");
				_logger.debug("location: " + location);
				url = new URL(location);
				connection = (HttpURLConnection)url.openConnection();
				connection.setDoInput(true);
				connection.connect();
			}
			
			String lastModified = connection.getHeaderField("Last-Modified");
			_logger.debug("lastModified: " + lastModified);
			Date date = null;
			if ((lastModified != null) && (lastModified.length() > 0)) {
				date = _lastModifiedFormat.parse(lastModified);
				File outputFile = new File(outputFileName);
				if (outputFile.exists()) {
					FileTime currentLastModified = Files.getLastModifiedTime(Paths.get(outputFileName));
					if (currentLastModified.toMillis() >= date.getTime()) {
						_logger.debug("Returning existing zip file name");
						return outputFileName;
					}
				}
			}
			
			inputStream = connection.getInputStream();
			outputStream = new FileOutputStream(outputFileName);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > 0) {
				outputStream.write(buffer, 0, bytesRead);
			}
			
			if (date != null) {
				Files.setLastModifiedTime(Paths.get(outputFileName), FileTime.fromMillis(date.getTime()));
			}
			
			return outputFileName;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void main(String [] args) {
		try {
			String stationId = "USM00072528";
			RadiosondeZipFileDAO dao = new RadiosondeZipFileDAO();
			String filePath = dao.getZipFile(stationId);
			System.out.println(filePath);
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
