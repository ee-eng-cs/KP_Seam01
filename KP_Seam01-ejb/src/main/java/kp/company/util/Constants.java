package kp.company.util;

import javax.xml.namespace.QName;

/**
 * Constants used in the application.
 */
public interface Constants {

	/**
	 * Web Service target namespace
	 */
	String TARGET_NAMESPACE = "http://service.xml.ws.company.kp/";
	/**
	 * service proxy name
	 */
	String SERVICE_NAME = "CompanyWSService";
	/**
	 * service proxy port
	 */
	String PORT_NAME = "CompanyWSPort";
	/**
	 * QName for the service
	 */
	QName Q_SERVICE_NAME = new QName(TARGET_NAMESPACE, SERVICE_NAME);
	/**
	 * QName for the port
	 */
	QName Q_PORT_NAME = new QName(TARGET_NAMESPACE, PORT_NAME);
	/**
	 * Web Service endpoint interface
	 */
	String ENDPOINT_INTERFACE = "kp.company.ws.xml.service.CompanyWS";
	/**
	 * URI specifying the location of the WSDL contract
	 */
	String ENDPOINT_ADDRESS_F = "http://%s:%s/KP_Seam01/ws/company%s";
	/**
	 * default host for Web Services
	 */
	String DEFAULT_HOST = "localhost";
	/**
	 * default port for Web Services
	 */
	String DEFAULT_PORT = "8080";
	/**
	 * default department name for WS lookup
	 */
	String DEFAULT_DEPARTMENT_NAME = "d-e-p-t-1";
	/**
	 * default employee last name for WS lookup
	 */
	String DEFAULT_EMPLOYEE_LASTNAME = "l-n-a-m-e-11";
	/**
	 * wildcard pattern for criteria predicate
	 */
	String LIKE_WILDCARD = "%%%s%%";
	/**
	 * part 1 of HTML output template for 'lookup employees by title'
	 */
	String SHOW_EMPLOYEES_1 = "<html>\n<head>\n<title>Employees Grouped By Title</title>\n"
			+ "</head>\n<body>\n<table border='1'>\n"
			+ "<caption>Employees Grouped By Title</caption>\n"
			+ "<tr><td>Title</td><td>Number</td></tr>\n";
	/**
	 * part 2 of HTML output template for 'lookup employees by title'
	 */
	String SHOW_EMPLOYEES_2 = "<tr><td>%s</td><td>%s</td></tr>\n";
	/**
	 * part 3 of HTML output template for 'lookup employees by title'
	 */
	String SHOW_EMPLOYEES_3 = "</table>\n</body>\n</html>\n";
}