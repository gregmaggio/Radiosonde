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

import ca.datamagic.radiosonde.dto.StateDTO;

/**
 * @author Greg
 *
 */
public class StateDAO {
	private static Logger _logger = LogManager.getLogger(StateDAO.class);
	private static Object _lockObj = new Object();
	private static Hashtable<String, StateDTO> _states = null;
	
	static {
		synchronized (_lockObj) {
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new FileReader("C:/Dev/Applications/Radiosonde/src/main/resources/data/igra2-us-states.txt"));
				//CountryDAO.class.getClassLoader().getResourceAsStream("classpath:data/igra2-us-states.txt");
				Hashtable<String, StateDTO> states = new Hashtable<String, StateDTO>();
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) {
					//CODE         1- 2    Character
					//NAME         4-50    Character
					StateDTO dto = new StateDTO();
					dto.setCode(currentLine.substring(0,  2).trim());
					dto.setName(currentLine.substring(3).trim());
					states.put(dto.getCode(), dto);
				}
				_states = states;
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
	
	public StateDAO() {
	}

	public StateDTO get(String code) {
		if (_states.containsKey(code)) {
			return _states.get(code);
		}
		return null;
	}
	
	public Enumeration<String> codes() {
		return _states.keys();
	}
	
	public static void main(String[] args) {
		try {
			StateDAO dao = new StateDAO();
			Enumeration<String> codes = dao.codes();
			while (codes.hasMoreElements()) {
				String code = codes.nextElement();
				StateDTO dto = dao.get(code);
				System.out.println(dto);
			}
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
