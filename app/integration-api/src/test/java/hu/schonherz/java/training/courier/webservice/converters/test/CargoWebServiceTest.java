package hu.schonherz.java.training.courier.webservice.converters.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.schonherz.java.training.courier.service.vo.AddressDetailsVO;
import hu.schonherz.java.training.courier.service.vo.AddressVO;
import hu.schonherz.java.training.courier.service.vo.CargoVO;
import hu.schonherz.java.training.courier.service.webservice.CargoWebServiceLocal;

public class CargoWebServiceTest {

	private static EJBContainer container;
	static CargoWebServiceLocal cargoWebServiceLocal;

	@BeforeClass
	public static void setUpClass() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		File[] ejbModules = new File[1];
		ejbModules[0] = new File("D:/Java/courier/app/integration-api/target/classes");
		// ejbModules[1] = new
		// File("D:/Java/courier/app/service/target/classes");
		properties.put(EJBContainer.MODULES, ejbModules);
		// properties.put("hu.neuron.java.jpa.hibernate.hbm2ddl.auto",
		// "create");
		// properties.put("hu.neuron.java.jpa.hibernate.dialect",
		// "org.hibernate.dialect.HSQLDialect");
		// properties.put("hu.neuron.TestDataSource",
		// "new://Resource?type=DataSource");
		// properties.put("hu.neuron.TestDataSource.JtaManaged", "false");
		// properties.put("hu.neuron.TestDataSource.JdbcDriver",
		// "org.hsqldb.jdbcDriver");
		// properties.put("hu.neuron.TestDataSource.JdbcUrl",
		// "jdbc:hsqldb:mem:aname");
		container = EJBContainer.createEJBContainer(properties);
		System.out.println("Opening the container");
		cargoWebServiceLocal = (CargoWebServiceLocal) container.getContext()
				.lookup("java:global/integration-api/CargoWebServiceImpl");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		container.close();
		System.out.println("Closing the container");
	}

	@Test
	public void testCargoWebServiceAccess() {
		Assert.assertNotNull(cargoWebServiceLocal);
	}

	@Test
	public void testGetCargosFromWebService() {
		List<CargoVO> cargos = cargoWebServiceLocal.getCargosListFromWebService();
		for (CargoVO cargoVO : cargos) {
			for (AddressVO ad : cargoVO.getAddresses()) {
				System.out.println(ad.getAddress());
				System.out.println(ad.getDeadline());
			}

		}
	}

}
