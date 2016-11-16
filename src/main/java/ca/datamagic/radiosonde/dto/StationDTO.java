package ca.datamagic.radiosonde.dto;

public class StationDTO {
	private String _id = null;
	private Double _latitude = null;
	private Double _longitude = null;
	private Double _elevation = null;
	private String _state = null;
	private String _name = null;
	private Integer _firstYear = null;
	private Integer _lastYear = null;
	private Integer _observations = null;
	
	public String getId() {
		return _id;
	}
	
	public void setId(String newVal) {
		_id = newVal;
	}
	
	public Double getLatitude() {
		return _latitude;
	}
	
	public void setLatitude(Double newVal) {
		_latitude = newVal;
	}
	
	public Double getLongitude() {
		return _longitude;
	}
	
	public void setLongitude(Double newVal) {
		_longitude = newVal;
	}
	
	public Double getElevation() {
		return _elevation;
	}
	
	public void setElevation(Double newVal) {
		_elevation = newVal;
	}
	
	public String getState() {
		return _state;
	}
	
	public void setState(String newVal) {
		_state = newVal;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String newVal) {
		_name = newVal;
	}
	
	public Integer getFirstYear() {
		return _firstYear;
	}
	
	public void setFirstYear(Integer newVal) {
		_firstYear = newVal;
	}
	
	public Integer getLastYear() {
		return _lastYear;
	}
	
	public void setLastYear(Integer newVal) {
		_lastYear = newVal;
	}
	
	public Integer getObservations() {
		return _observations;
	}
	
	public void setObservations(Integer newVal) {
		_observations = newVal;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(_id);
		buffer.append(",");
		buffer.append(_latitude);
		buffer.append(",");
		buffer.append(_longitude);
		buffer.append(",");
		buffer.append(_elevation);
		buffer.append(",");
		buffer.append(_state);
		buffer.append(",");
		buffer.append(_name);
		buffer.append(",");
		buffer.append(_firstYear);
		buffer.append(",");
		buffer.append(_lastYear);
		buffer.append(",");
		buffer.append(_observations);
		return buffer.toString();
	}
}
