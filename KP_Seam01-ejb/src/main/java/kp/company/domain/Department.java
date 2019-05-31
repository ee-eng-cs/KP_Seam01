package kp.company.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/*
 * In a bidirectional relationship the owner is responsible for the association column(s) update.
 * To declare a side as not responsible for the relationship, the attribute "mappedBy" is used.
 * "mappedBy" refers to the property name of the association on the owner side.
 * 
 * Java EE 6 Bean Validation allows to define constraints once (on the entity)
 * and have them applied in all layers of the application.
 * The error messages from Bean Validation are automatically attached to the relevant field by JSF,
 * and adding a messages JSF component will display them.
 */
/**
 * Simple JavaBean business object representing a department.
 * 
 */
@Entity
public class Department implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 25, message = "{dep.name.Size}")
	@Pattern(regexp = "[A-Za-z0-9-]*", message = "{dep.name.Pattern}")
	private String name;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Employee> employees = new ArrayList<Employee>();

	/**
	 * Sets id.
	 * 
	 * @param id
	 *            the id
	 */
	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * Gets id.
	 * 
	 * @return the id
	 */
	public Long getId() {

		return id;
	}

	/**
	 * Sets name.
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets name.
	 * 
	 * @return the name
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * Sets employees.
	 * 
	 * @param employees
	 *            the employees list
	 */
	public void setEmployees(List<Employee> employees) {

		this.employees = employees;
	}

	/**
	 * Gets employees.
	 * 
	 * @return the employees list
	 */
	@XmlElements(@XmlElement(name = "employee"))
	public List<Employee> getEmployees() {

		return employees;
	}

	/**
	 * Adds employee.
	 * 
	 * @param employee
	 *            the employee
	 */
	public void addEmployee(Employee employee) {

		employees.add(employee);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employee
	 *            the employee
	 */
	public void removeEmployee(Employee employee) {

		employees.remove(employee);
	}
}
