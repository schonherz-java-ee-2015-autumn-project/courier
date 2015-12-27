package hu.schonherz.java.training.courier.web.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import hu.schonherz.java.training.courier.service.QuartzManagerRemote;

public class SchedulerServlet extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(SchedulerServlet.class);
	@EJB(name = "QuartzManager")
	QuartzManagerRemote qm;

	private List<String> methodNames = new ArrayList<>();
	private List<String> methodFrequencies = new ArrayList<>();

	// Ez a Servlet az alkalmazás indításakor elindul és megcsinálja nekünk a
	// Schedulert
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String cronExpr, methodName;
		// beadjuk a metódus neveket.
		methodNames.add("getUsersMethodName");
		methodNames.add("getFreeCargosMethodName");
		methodFrequencies.add("getUsersFrequency");
		methodFrequencies.add("getFreeCargosFrequency");

		try {
			for (int i = 0; i < methodNames.size(); i++) {
				cronExpr = getInitParameter(methodFrequencies.get(i));
				methodName = getInitParameter(methodNames.get(i));
				qm.initialize(cronExpr, methodName);
				logger.info("INFO: Method" + methodName + " with " + cronExpr + " frequency will be called.");
			}

		} catch (Exception e) {

		}
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

	}

}
