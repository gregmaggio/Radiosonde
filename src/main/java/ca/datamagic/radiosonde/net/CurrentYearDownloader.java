/**
 * 
 */
package ca.datamagic.radiosonde.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Calendar;

/**
 * @author Greg
 *
 */
public class CurrentYearDownloader {	
	public CurrentYearDownloader() {
	}

	public static String downloadZip(String stationId) throws IOException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String fileName = MessageFormat.format("{0}-data-beg{1}.txt.zip", stationId, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
			String dataDirectory = "C:/Dev/Applications/Radiosonde/src/main/resources/data";
			String outputFileName = MessageFormat.format("{0}/{1}", dataDirectory, fileName);
			URL url = new URL(MessageFormat.format("http://www1.ncdc.noaa.gov/pub/data/igra/data/data-y2d/{0}", fileName));
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			//String lastModified = connection.getHeaderField("Last-Modified");
			//Date date = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(lastModified);
			//BasicFileAttributeView attributes = Files.getFileAttributeView(Paths.get(filePath), BasicFileAttributeView.class);
		    //FileTime time = FileTime.fromMillis(creationDate.getTime());
		    //attributes.setTimes(time, time, time);
		    
			inputStream = connection.getInputStream();
			outputStream = new FileOutputStream(outputFileName);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > 0) {
				outputStream.write(buffer, 0, bytesRead);
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
	
	public static void main(String[] args) {
		try {
			String filePath = downloadZip("USM00072528");
			System.out.println(filePath);
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
