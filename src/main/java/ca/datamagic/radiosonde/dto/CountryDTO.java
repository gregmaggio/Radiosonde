/**
 * 
 */
package ca.datamagic.radiosonde.dto;

/**
 * @author Greg
 *
 */
public class CountryDTO {
	private String _code = null;
	private String _name = null;

	public String getCode() {
		return _code;
	}
	
	public void setCode(String newVal) {
		_code = newVal;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String newVal) {
		_name = newVal;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(_code);
		buffer.append(",");
		buffer.append(_name);
		return buffer.toString();
	}
}
