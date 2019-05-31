package kp.company.ws.xml.service;

import static kp.company.util.Constants.ENDPOINT_INTERFACE;
import static kp.company.util.Constants.PORT_NAME;
import static kp.company.util.Constants.SERVICE_NAME;
import static kp.company.util.Constants.TARGET_NAMESPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.ws.WSResponseWrapper;
import kp.company.ws.xml.XmlBusinessLogic;

/**
 * Company Web Service. Service Endpoint Implementation.
 * 
 * The static client programming model for JAX-WS is called the proxy client.
 * The proxy client invokes a Web service based on a SEI.
 * 
 */
@RequestScoped
@WebService(endpointInterface = ENDPOINT_INTERFACE, portName = PORT_NAME, serviceName = SERVICE_NAME, targetNamespace = TARGET_NAMESPACE)
public class CompanyWSImpl implements CompanyWS {

	private Logger log = Logger.getLogger(CompanyWSImpl.class.getName());

	private static final String BEAN_NAME = "java:app/KP_Seam01-ejb-1.0.0-SNAPSHOT/XmlBusinessLogic";

	/**
	 * Lookup Employees.
	 * 
	 * @param department
	 *            the department
	 * @param employee
	 *            the employee
	 * @return the response wrapper list
	 */
	public List<WSResponseWrapper> lookupEmployees(Department department,
			Employee employee) {

		XmlBusinessLogic xmlBusinessLogic = null;
		try {
			Context initialContext = new InitialContext();
			xmlBusinessLogic = (XmlBusinessLogic) initialContext
					.lookup(BEAN_NAME);
		} catch (NamingException ex) {
			ex.printStackTrace();
			return new ArrayList<WSResponseWrapper>();
		}
		List<WSResponseWrapper> responseWrapperList = xmlBusinessLogic
				.lookupEmployees(department, employee);
		log.info("lookupEmployees(): responseWrapperList size["
				+ responseWrapperList.size() + "]");
		return responseWrapperList;
	}
}