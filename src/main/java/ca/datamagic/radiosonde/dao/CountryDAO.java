/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.datamagic.radiosonde.dto.CountryDTO;

/**
 * @author Greg
 *
 */
public class CountryDAO {
	private static Logger _logger = LogManager.getLogger(CountryDAO.class);
	private static Object _lockObj = new Object();
	private static Hashtable<String, CountryDTO> _countries = null;
	
	static {
		synchronized (_lockObj) {
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new FileReader("C:/Dev/Applications/Radiosonde/src/main/resources/data/igra2-country-list.txt"));
				//CountryDAO.class.getClassLoader().getResourceAsStream("classpath:data/igra2-country-list.txt");
				Hashtable<String, CountryDTO> countries = new Hashtable<String, CountryDTO>();
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) {
					//CODE         1- 2    Character
					//NAME         4-43    Character
					CountryDTO dto = new CountryDTO();
					dto.setCode(currentLine.substring(0,  2).trim());
					dto.setName(currentLine.substring(3).trim());
					countries.put(dto.getCode(), dto);
				}
				_countries = countries;
			} catch (Throwable t) {
				_logger.error("Exception", t);
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
	}
	
	public CountryDAO() {
	}

	public CountryDTO get(String code) {
		if (_countries.containsKey(code)) {
			return _countries.get(code);
		}
		return null;
	}
	
	public Enumeration<String> codes() {
		return _countries.keys();
	}
	
	public static void main(String[] args) {
		try {
			CountryDAO dao = new CountryDAO();
			Enumeration<String> codes = dao.codes();
			while (codes.hasMoreElements()) {
				String code = codes.nextElement();
				CountryDTO dto = dao.get(code);
				System.out.println(dto);
			}
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
