package kp.company.ws.xml.client;

import static kp.company.util.Constants.DEFAULT_DEPARTMENT_NAME;
import static kp.company.util.Constants.DEFAULT_EMPLOYEE_LASTNAME;
import static kp.company.util.Constants.DEFAULT_PORT;
import static kp.company.util.Constants.ENDPOINT_ADDRESS_F;
import static kp.company.util.Constants.Q_PORT_NAME;
import static kp.company.util.Constants.Q_SERVICE_NAME;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.ws.EndpointData;
import kp.company.ws.WSResponseWrapper;
import kp.company.ws.xml.service.CompanyWS;

/**
 * Company WS Client.
 */
@Model
public class CompanyWSClient implements Serializable {

	private Logger log = Logger.getLogger(CompanyWSClient.class.getName());

	private static final long serialVersionUID = 1L;

	private String departmentName;

	private String employeeLastName;

	@Produces
	@Named
	private List<Employee> employeesWS;

	@Inject
	EndpointData endpointData;

	/**
	 * Department name getter.
	 * 
	 * @return the department name
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * Department name setter.
	 * 
	 * @param departmentName
	 *            the department name to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Employee last name getter.
	 * 
	 * @return the employee last name
	 */
	public String getEmployeeLastName() {
		return employeeLastName;
	}

	/**
	 * Employee last name setter.
	 * 
	 * @param employeeLastName
	 *            the employee last name to set
	 */
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	/**
	 * Shows XML Web Services.
	 * 
	 * @return the outcome
	 */
	public String showXMLWebServicesAction() {

		departmentName = DEFAULT_DEPARTMENT_NAME;
		employeeLastName = DEFAULT_EMPLOYEE_LASTNAME;
		log.info("showXMLWebServicesAction():");
		return "JAX-WS";
	}

	/**
	 * Lookup action.
	 * 
	 * @return the outcome
	 */
	public String lookupAction() {

		CompanyWS companyWSProxy = null;
		companyWSProxy = getServiceProxy();
		if (companyWSProxy == null) {
			log.severe("lookupAction(): service proxy is null");
			return "JAX-WS";
		}
		changeEndpointAddress(companyWSProxy);

		Department department = new Department();
		department.setName(departmentName);
		Employee employee = new Employee();
		employee.setLastName(employeeLastName);

		List<WSResponseWrapper> responseWrapperList = companyWSProxy
				.lookupEmployees(department, employee);
		if (responseWrapperList == null || responseWrapperList.isEmpty()) {
			log.info("lookupAction(): empty WS response");
			return "JAX-WS";
		}
		employeesWS = new ArrayList<Employee>();
		for (WSResponseWrapper rw : responseWrapperList) {
			// 'employee' object from WS answer is without 'department'
			rw.employee.setDepartment(rw.department);
			employeesWS.add(rw.employee);
		}
		log.info("lookupAction(): employees size[" + employeesWS.size() + "]");
		return "JAX-WS";
	}

	/**
	 * Gets service proxy with WSDL contract.
	 * 
	 * @return the service proxy
	 */
	private CompanyWS getServiceProxy() {

		URL wsdlDocumentLocation = null;
		try {
			wsdlDocumentLocation = new URL(String.format(ENDPOINT_ADDRESS_F,
					endpointData.getHost(), DEFAULT_PORT, "?wsdl"));
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		Service service = Service.create(wsdlDocumentLocation, Q_SERVICE_NAME);
		CompanyWS companyWSProxy = service
				.getPort(Q_PORT_NAME, CompanyWS.class);

		log.info("getServiceProxy(): wsdlDocumentLocation["
				+ wsdlDocumentLocation + "]");
		return companyWSProxy;
	}

	/**
	 * Check and change service endpoint address loaded from WSDL.
	 * 
	 * @param companyWSProxy
	 *            the service proxy
	 */
	private void changeEndpointAddress(CompanyWS companyWSProxy) {

		BindingProvider bindingProvider = (BindingProvider) companyWSProxy;
		Map<String, Object> contextMap = bindingProvider.getRequestContext();
		String sourceEndpointAddress = (String) contextMap
				.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		String targetEndpointAddress = String.format(ENDPOINT_ADDRESS_F,
				endpointData.getHost(), DEFAULT_PORT, "");

		if (targetEndpointAddress.equals(sourceEndpointAddress)) {
			// address is OK
			return;
		}
		// dynamically change the service endpoint address
		contextMap.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				targetEndpointAddress);
		log.info("changeEndpointAddress(): changed endpoint from address["
				+ sourceEndpointAddress + "] to address["
				+ targetEndpointAddress + "]");
	}

}
