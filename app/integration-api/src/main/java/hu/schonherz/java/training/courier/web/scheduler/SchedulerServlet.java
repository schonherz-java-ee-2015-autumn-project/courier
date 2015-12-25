package hu.schonherz.java.training.courier.web.scheduler;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import hu.schonherz.java.training.courier.service.QuartzManagerRemote;

public class SchedulerServlet extends GenericServlet {

	@EJB(name="QuartzManager")
	QuartzManagerRemote qm;

	//Ez a Servlet az alkalmaz�s ind�t�sakor elindul �s megcsin�lja nek�nk a Schedulert
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		
		try {
			String cronExpr = null;
			cronExpr = getInitParameter("cronExpr");
			System.out.println(cronExpr);

			qm.initialize(cronExpr);

			
		} catch (Exception e) {
			
		}
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

	}

}
