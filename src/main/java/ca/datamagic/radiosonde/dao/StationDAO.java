/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.datamagic.radiosonde.dto.StationDTO;
import ca.datamagic.radiosonde.inject.MemoryCache;

/**
 * @author Greg
 *
 */
public class StationDAO extends BaseDAO {
	private static Logger _logger = LogManager.getLogger(StationDAO.class);
	private Hashtable<String, StationDTO> _stations = null;
	
	public StationDAO() {
		initialize();
	}

	private synchronized void initialize() {
		LineNumberReader reader = null;
		try {
			String dataFile = MessageFormat.format("{0}/igra2-station-list.txt", getDataPath());
			reader = new LineNumberReader(new FileReader(dataFile));
			Hashtable<String, StationDTO> stations = new Hashtable<String, StationDTO>();
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {
				//ID            1-11   Character
				//LATITUDE     13-20   Real
				//LONGITUDE    22-30   Real
				//ELEVATION    32-37   Real
				//STATE        39-40   Character
				//NAME         42-71   Character
				//FSTYEAR      73-76   Integer
				//LSTYEAR      78-81   Integer
				//NOBS         83-88   Integer
				String name = currentLine.substring(38, 71).trim();
				StationDTO dto = new StationDTO();
				dto.setId(currentLine.substring(0, 11).trim());
				dto.setLatitude(new Double(currentLine.substring(12, 20).trim()));
				dto.setLongitude(new Double(currentLine.substring(21, 30).trim()));
				dto.setElevation(new Double(currentLine.substring(31, 37).trim()));
				if (name.charAt(2) == ' ') {
					dto.setState(name.substring(0, 2).trim());
					dto.setName(name.substring(2).trim());
				} else {
					dto.setState("");
					dto.setName(name);
				}
				dto.setFirstYear(new Integer(currentLine.substring(72, 76).trim()));
				dto.setLastYear(new Integer(currentLine.substring(77, 81).trim()));
				dto.setObservations(new Integer(currentLine.substring(82, 88).trim()));
				stations.put(dto.getId(), dto);
			}
			_stations = stations;
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
	
	@MemoryCache
	public StationDTO get(String id) {
		if (_stations.containsKey(id)) {
			return _stations.get(id);
		}
		return null;
	}
	
	@MemoryCache
	public Enumeration<String> ids() {
		return _stations.keys();
	}
	
	@MemoryCache
	public Collection<StationDTO> list() {
		return _stations.values();
	}
	
	public static void main(String[] args) {
		try {
			StationDAO dao = new StationDAO();
			Enumeration<String> ids = dao.ids();
			while (ids.hasMoreElements()) {
				String id = ids.nextElement();
				StationDTO dto = dao.get(id);
				System.out.println(dto);
			}
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}
}
