/**
 * 
 */
package ca.datamagic.radiosonde.dto;

/**
 * @author Greg
 *
 */
public class RadiosondeRecordDTO {
	private Integer _recordNo = null;
	private Integer _majorLevelType = null;
	private Integer _minorLevelType = null;
	private Integer _elapsedTime = null;
	private Integer _pressure = null;
	private String _pressureFlag = null;
	private Integer _geopotentialHeight = null;
	private String _geopotentialHeightFlag = null;
	private Integer _temperature = null;
	private String _temperatureFlag = null;
	private Integer _relativeHumidity = null;
	private Integer _dewpoint = null;
	private Integer _windDirection = null;
	private Integer _windSpeed = null;
	
	public Integer getRecordNo() {
		return _recordNo;
	}
	
	public Integer getMajorLevelType() {
		return _majorLevelType;
	}
	
	public Integer getMinorLevelType() {
		return _minorLevelType;
	}
	
	public Integer getElapsedTime() {
		return _elapsedTime;
	}
	
	public Integer getPressure() {
		return _pressure;
	}
	
	public String getPressureFlag() {
		return _pressureFlag;
	}
	
	public Integer getGeopotentialHeight() {
		return _geopotentialHeight;
	}
	
	public String getGeopotentialHeightFlag() {
		return _geopotentialHeightFlag;
	}
	
	public Integer getTemperature() {
		return _temperature;
	}
	
	public String getTemperatureFlag() {
		return _temperatureFlag;
	}
	
	public Integer getRelativeHumidity() {
		return _relativeHumidity;
	}
	
	public Integer getDewpoint() {
		return _dewpoint;
	}
	
	public Integer getWindDirection() {
		return _windDirection;
	}
	
	public Integer getWindSpeed() {
		return _windSpeed;
	}
	
	public void setRecordNo(Integer newVal) {
		_recordNo = newVal;
	}
	
	public void setMajorLevelType(Integer newVal) {
		_majorLevelType = newVal;
	}
	
	public void setMinorLevelType(Integer newVal) {
		_minorLevelType = newVal;
	}
	
	public void setElapsedTime(Integer newVal) {
		_elapsedTime = newVal;
	}
	
	public void setPressure(Integer newVal) {
		_pressure = newVal;
	}
	
	public void setPressureFlag(String newVal) {
		_pressureFlag = newVal;
	}
	
	public void setGeopotentialHeight(Integer newVal) {
		_geopotentialHeight = newVal;
	}
	
	public void setGeopotentialHeightFlag(String newVal) {
		_geopotentialHeightFlag = newVal;
	}
	
	public void setTemperature(Integer newVal) {
		_temperature = newVal;
	}
	
	public void setTemperatureFlag(String newVal) {
		_temperatureFlag = newVal;
	}
	
	public void setRelativeHumidity(Integer newVal) {
		_relativeHumidity = newVal;
	}
	
	public void setDewpoint(Integer newVal) {
		_dewpoint = newVal;
	}
	
	public void setWindDirection(Integer newVal) {
		_windDirection = newVal;
	}
	
	public void setWindSpeed(Integer newVal) {
		_windSpeed = newVal;
	}
}
