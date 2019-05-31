package kp.company.ws;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kp.company.domain.Department;
import kp.company.domain.Employee;

/**
 * Web Services Response wrapper.
 * 
 */
@XmlRootElement
public class WSResponseWrapper {

	/**
	 * the department
	 */
	@XmlElement
	public Department department;

	/**
	 * the employee
	 */
	@XmlElement
	public Employee employee;

	/**
	 * additional information
	 */
	@XmlAttribute
	public String info;

	/**
	 * No-argument constructor
	 * 
	 */
	public WSResponseWrapper() {
	}

	/**
	 * Constructor with 'Department' argument.
	 * 
	 * @param department
	 *            the department
	 */
	public WSResponseWrapper(Department department) {

		this.department = department;
		this.info = department.getName();
	}

	/**
	 * Constructor with 'Employee' argument.
	 * 
	 * @param employee
	 *            the employee
	 */
	public WSResponseWrapper(Employee employee) {

		this.department = employee.getDepartment();
		this.employee = employee;
		StringBuilder strBuf = new StringBuilder();
		strBuf.append(employee.getFirstName()).append(" ");
		strBuf.append(employee.getLastName()).append(" from ");
		strBuf.append(department.getName());
		this.info = strBuf.toString();
	}
}
