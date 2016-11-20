/**
 * 
 */
package ca.datamagic.radiosonde.dao;

import java.text.MessageFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Greg
 *
 */
public class RadiosondeZipFileDAOTester {
	private static Logger _logger = LogManager.getLogger(RadiosondeZipFileDAOTester.class);
	private RadiosondeZipFileDAO _dao = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/META-INF/log4j.cfg.xml");
		String executionPath = System.getProperty("user.dir");
		String dataPath = MessageFormat.format("{0}/src/test/resources/data", executionPath.replace("\\", "/"));
		_logger.debug("dataPath: " + dataPath);
		BaseDAO.setDataPath(dataPath);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_dao = new RadiosondeZipFileDAO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		_dao = null;
	}

	@Test
	public void test1() throws Exception {
		String stationId = "USM00072403";
		_logger.debug("stationId: " + stationId);
		
		String zipFile = _dao.getZipFile(stationId);
		_logger.debug("zipFile: " + zipFile);
	}

}
