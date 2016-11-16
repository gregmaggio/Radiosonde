/**
 * 
 */
package ca.datamagic.radiosonde.dao;

/**
 * @author Greg
 *
 */
public abstract class BaseDAO {
	private static String _dataPath = "C:/Dev/Applications/Radiosonde/src/main/resources/data";
	
	public static String getDataPath() {
		return _dataPath;
	}
	
	public static void setDataPath(String newVal) {
		_dataPath = newVal;
	}
}
