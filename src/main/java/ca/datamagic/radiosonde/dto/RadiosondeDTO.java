/**
 * 
 */
package ca.datamagic.radiosonde.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Greg
 *
 */
public class RadiosondeDTO {
	private String _id = null;
	private Integer _year = null;
	private Integer _month = null;
	private Integer _day = null;
	private Integer _hour = null;
	private String _releaseTime = null;
	private Integer _recordCount = null;
	private String _pressureSource = null;
	private String _nonPressureSource = null;
	private Integer _latitude = null;
	private Integer _longitude = null;
	private List<RadiosondeRecordDTO> _records = new ArrayList<RadiosondeRecordDTO>();
	
	public String getId() {
		return _id;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public Integer getMonth() {
		return _month;
	}
	
	public Integer getDay() {
		return _day;
	}
	
	public Integer getHour() {
		return _hour;
	}
	
	public String getReleaseTime() {
		return _releaseTime;
	}
	
	public Integer getRecordCount() {
		return _recordCount;
	}
	
	public String getPressureSource() {
		return _pressureSource;
	}
	
	public String getNonPressureSource() {
		return _nonPressureSource;
	}
	
	public Integer getLatitude() {
		return _latitude;
	}
	
	public Integer getLongitude() {
		return _longitude;
	}
	
	@JsonIgnore
	public List<RadiosondeRecordDTO> getRecords() {
		return _records;
	}
	
	public void setId(String newVal) {
		_id = newVal;
	}
	
	public void setYear(Integer newVal) {
		_year = newVal;
	}
	
	public void setMonth(Integer newVal) {
		_month = newVal;
	}
	
	public void setDay(Integer newVal) {
		_day = newVal;
	}
	
	public void setHour(Integer newVal) {
		_hour = newVal;
	}
	
	public void setReleaseTime(String newVal) {
		_releaseTime = newVal;
	}
	
	public void setRecordCount(Integer newVal) {
		_recordCount = newVal;
	}
	
	public void setPressureSource(String newVal) {
		_pressureSource = newVal;
	}
	
	public void setNonPressureSource(String newVal) {
		_nonPressureSource = newVal;
	}
	
	public void setLatitude(Integer newVal) {
		_latitude = newVal;
	}
	
	public void setLongitude(Integer newVal) {
		_longitude = newVal;
	}
	
	@JsonIgnore
	public void setRecords(List<RadiosondeRecordDTO> newVal) {
		_records = newVal;
	}
}
