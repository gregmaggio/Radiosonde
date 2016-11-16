/**
 * 
 */
package ca.datamagic.radiosonde.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.radiosonde.dao.BaseDAO;
import ca.datamagic.radiosonde.dao.RadiosondeDAO;
import ca.datamagic.radiosonde.dao.RadiosondeDataFileDAO;
import ca.datamagic.radiosonde.dao.RadiosondeZipFileDAO;
import ca.datamagic.radiosonde.dao.StationDAO;
import ca.datamagic.radiosonde.dto.RadiosondeDTO;
import ca.datamagic.radiosonde.dto.RadiosondeRecordDTO;
import ca.datamagic.radiosonde.dto.StationDTO;
import ca.datamagic.radiosonde.dto.SwaggerConfigurationDTO;
import ca.datamagic.radiosonde.dto.SwaggerResourceDTO;
import ca.datamagic.radiosonde.inject.DAOModule;

/**
 * @author Greg
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	private static Logger _logger = LogManager.getLogger(IndexController.class);
	private static Injector _injector = null;
	private static StationDAO _stationDAO = null;
	private static RadiosondeZipFileDAO _radiosondeZipFileDAO = null;
	private static RadiosondeDataFileDAO _radiosondeDataFileDAO = null;
	private static RadiosondeDAO _radiosondeDAO = null;
	private static SwaggerConfigurationDTO _swaggerConfiguration = null;
	private static SwaggerResourceDTO[] _swaggerResources = null;
	private static String _swagger = null;
	
	static {
		FileInputStream swaggerStream = null;
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();       
		    Resource dataResource = loader.getResource("classpath:data");
		    Resource metaInfResource = loader.getResource("META-INF");
		    String dataPath = dataResource.getFile().getAbsolutePath();
		    String metaInfPath = metaInfResource.getFile().getAbsolutePath();
		    _logger.debug("dataPath: " + dataPath);
		    _logger.debug("metaInfPath: " + metaInfPath);
		    
		    String swaggerFileName = MessageFormat.format("{0}/swagger.json", metaInfPath);
		    swaggerStream = new FileInputStream(swaggerFileName);
		    _swagger = IOUtils.toString(swaggerStream, "UTF-8");
		    
		    BaseDAO.setDataPath(dataPath);
			_injector = Guice.createInjector(new DAOModule());
			_stationDAO = _injector.getInstance(StationDAO.class);
			_radiosondeZipFileDAO = _injector.getInstance(RadiosondeZipFileDAO.class);
			_radiosondeDataFileDAO = _injector.getInstance(RadiosondeDataFileDAO.class);
			_radiosondeDAO = _injector.getInstance(RadiosondeDAO.class);
			_swaggerConfiguration = new SwaggerConfigurationDTO();
			_swaggerResources = new SwaggerResourceDTO[] { new SwaggerResourceDTO() };
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
		if (swaggerStream != null) {
			IOUtils.closeQuietly(swaggerStream);
		}
	}
	
	public IndexController() {
	}

	@RequestMapping(value="/api/stations", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Collection<StationDTO> listStations() {
		return _stationDAO.list();
	}
	
	@RequestMapping(value="/api/station/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public StationDTO getStation(@PathVariable String id) {
		return _stationDAO.get(id);
	}
	
	@RequestMapping(value="/api/radiosondes/{stationId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<RadiosondeDTO> getRadiosondes(@PathVariable String stationId) throws IOException, ParseException {
		String zipFile = _radiosondeZipFileDAO.getZipFile(stationId);
		if (zipFile != null) {
			String dataFile = _radiosondeDataFileDAO.getDataFile(zipFile);
			return _radiosondeDAO.load(dataFile);
		}
		return null;
	}
	
	@RequestMapping(value="/api/radiosonde/{stationId}/{year}/{month}/{day}/{hour}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<RadiosondeRecordDTO> getRadiosondeRecords(@PathVariable String stationId, @PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String hour) throws IOException, ParseException {
		year = year.trim();
		month = month.trim();
		day = day.trim();
		hour = hour.trim();
		if (month.startsWith("0")) {
			month = month.substring(1);
		}
		if (day.startsWith("0")) {
			day = day.substring(1);
		}
		if (hour.startsWith("0")) {
			hour = hour.substring(1);
		}
		int intYear = Integer.parseInt(year);
		int intMonth = Integer.parseInt(month);
		int intDay = Integer.parseInt(day);
		int intHour = Integer.parseInt(hour);
		String zipFile = _radiosondeZipFileDAO.getZipFile(stationId);
		if (zipFile != null) {
			String dataFile = _radiosondeDataFileDAO.getDataFile(zipFile);
			List<RadiosondeDTO> radiosondes = _radiosondeDAO.load(dataFile);
			for (int ii = 0; ii < radiosondes.size(); ii++) {
				if ((radiosondes.get(ii).getYear() != null) &&
					(radiosondes.get(ii).getMonth() != null) &&
					(radiosondes.get(ii).getDay() != null) &&
					(radiosondes.get(ii).getHour() != null)) {
					if ((radiosondes.get(ii).getYear().intValue() == intYear) &&
						(radiosondes.get(ii).getMonth().intValue() == intMonth) &&
						(radiosondes.get(ii).getDay().intValue() == intDay) &&
						(radiosondes.get(ii).getHour().intValue() == intHour)) {
						return radiosondes.get(ii).getRecords();
					}
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value="/swagger-resources/configuration/ui", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerConfigurationDTO getSwaggerConfigurationUI() {
		return _swaggerConfiguration;
	}
	
	@RequestMapping(value="/swagger-resources", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerResourceDTO[] getSwaggerResources() {
		return _swaggerResources;
	}
	
	@RequestMapping(value="/v2/api-docs", method=RequestMethod.GET, produces="application/json")
	public void getSwagger(Writer responseWriter) throws IOException {
		responseWriter.write(_swagger);
	}
}
