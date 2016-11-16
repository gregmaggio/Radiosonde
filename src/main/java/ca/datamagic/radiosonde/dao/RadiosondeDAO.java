/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.datamagic.radiosonde.dto.RadiosondeDTO;
import ca.datamagic.radiosonde.dto.RadiosondeRecordDTO;

/**
 * @author Greg
 *
 */
public class RadiosondeDAO {
	private static Logger _logger = LogManager.getLogger(RadiosondeDAO.class);
	
	public RadiosondeDAO() {
	}

	public List<RadiosondeDTO> load(String fileName) throws IOException {
		LineNumberReader reader = null;
		try {
			List<RadiosondeDTO> radiosondes = new ArrayList<RadiosondeDTO>();
			reader = new LineNumberReader(new FileReader(fileName));
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {
				System.out.println("lineNumber: " + reader.getLineNumber());
				//HEADREC       1-  1  Character
				//ID            2- 12  Character
				//YEAR         14- 17  Integer
				//MONTH        19- 20  Integer
				//DAY          22- 23  Integer
				//HOUR         25- 26  Integer
				//RELTIME      28- 31  Integer
				//NUMLEV       33- 36  Integer
				//P_SRC        38- 45  Character
				//NP_SRC       47- 54  Character
				//LAT          56- 62  Integer
				//LON          64- 71  Integer
				String headerRecord = currentLine.substring(0, 1).trim();
				if (headerRecord.compareToIgnoreCase("#") == 0) {
					String year = currentLine.substring(13, 17).trim();
					String month = currentLine.substring(18, 20).trim();
					String day = currentLine.substring(21, 23).trim();
					String hour = currentLine.substring(24, 26).trim();
					if (month.startsWith("0")) {
						month = month.substring(1);
					}
					if (day.startsWith("0")) {
						day = day.substring(1);
					}
					if (hour.startsWith("0")) {
						hour = hour.substring(1);
					}
					RadiosondeDTO radiosonde = new RadiosondeDTO();
					radiosonde.setId(currentLine.substring(1, 12).trim());
					radiosonde.setYear(new Integer(year));
					radiosonde.setMonth(new Integer(month));
					radiosonde.setDay(new Integer(day));
					radiosonde.setHour(new Integer(hour));
					radiosonde.setReleaseTime(currentLine.substring(27, 31).trim());
					radiosonde.setRecordCount(new Integer(currentLine.substring(32, 36).trim()));
					radiosonde.setPressureSource(currentLine.substring(37, 45).trim());
					radiosonde.setNonPressureSource(currentLine.substring(46, 54).trim());
					radiosonde.setLatitude(new Integer(currentLine.substring(55, 62).trim()));
					radiosonde.setLongitude(new Integer(currentLine.substring(63, 71).trim()));
					int recordCount = radiosonde.getRecordCount().intValue();
					for (int ii = 0; ii < recordCount; ii++) {
						currentLine = reader.readLine();
						if (currentLine == null) {
							break;
						}
						System.out.println("lineNumber: " + reader.getLineNumber());
						//LVLTYP1         1-  1   Integer
						//LVLTYP2         2-  2   Integer
						//ETIME           4-  8   Integer
						//PRESS          10- 15   Integer
						//PFLAG          16- 16   Character
						//GPH            17- 21   Integer
						//ZFLAG          22- 22   Character
						//TEMP           23- 27   Integer
						//TFLAG          28- 28   Character
						//RH             29- 33   Integer
						//DPDP           35- 39   Integer
						//WDIR           41- 45   Integer
						//WSPD           47- 51   Integer
						RadiosondeRecordDTO record = new RadiosondeRecordDTO();
						record.setRecordNo(ii + 1);
						record.setMajorLevelType(new Integer(currentLine.substring(0, 1).trim()));
						record.setMinorLevelType(new Integer(currentLine.substring(1, 2).trim()));
						record.setElapsedTime(new Integer(currentLine.substring(3, 8).trim()));
						record.setPressure(new Integer(currentLine.substring(9, 15).trim()));
						record.setPressureFlag(currentLine.substring(15, 16).trim());
						record.setGeopotentialHeight(new Integer(currentLine.substring(16, 21).trim()));
						record.setGeopotentialHeightFlag(currentLine.substring(21, 22).trim());
						record.setTemperature(new Integer(currentLine.substring(22, 27).trim()));
						record.setTemperatureFlag(currentLine.substring(27, 28).trim());
						record.setRelativeHumidity(new Integer(currentLine.substring(28, 33).trim()));
						record.setDewpoint(new Integer(currentLine.substring(34, 39).trim()));
						record.setWindDirection(new Integer(currentLine.substring(40, 45).trim()));
						record.setWindSpeed(new Integer(currentLine.substring(46, 51).trim()));
						radiosonde.getRecords().add(record);
					}
					radiosondes.add(radiosonde);
					if (currentLine == null) {
						break;
					}
				}
			}
			return radiosondes;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Throwable t) {
					_logger.warn("Exception", t);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			String fileName = "C:/Program Files (x86)/Apache Software Foundation/apache-tomcat-7.0.50/webapps/Radiosonde/WEB-INF/classes/data/USM00072528-data.txt";
			RadiosondeDAO dao = new RadiosondeDAO();
			List<RadiosondeDTO> radiosondes = dao.load(fileName);
			System.out.println("radiosondes: " + radiosondes.size());
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
