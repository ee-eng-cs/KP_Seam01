package kp.company.ws.xml.service;

import static kp.company.util.Constants.TARGET_NAMESPACE;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.ws.WSResponseWrapper;

/**
 * Company Web Service. Service Endpoint Interface.
 * 
 */
@WebService(name = "CompanyWS", targetNamespace = TARGET_NAMESPACE)
public interface CompanyWS {

	/**
	 * Lookup employees.
	 * 
	 * @param department
	 *            the department
	 * @param employee
	 *            the employee
	 * @return the employees list
	 */
	@WebMethod
	@WebResult(name = "responseWrapperList", targetNamespace = TARGET_NAMESPACE)
	public List<WSResponseWrapper> lookupEmployees(
			@WebParam(name = "lookupDepartment", targetNamespace = TARGET_NAMESPACE) Department department,
			@WebParam(name = "lookupEmployee", targetNamespace = TARGET_NAMESPACE) Employee employee);
}
