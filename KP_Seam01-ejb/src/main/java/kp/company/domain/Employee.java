package kp.company.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Simple JavaBean domain object representing an employee. The 'equals()' and
 * 'hashCode()' methods are overridden because instances of subclasses are in
 * Sets.
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@Size(min = 3, max = 25, message = "{emp.firstName.Size}")
	@Pattern(regexp = "[A-Za-z0-9-]*", message = "{emp.firstName.Pattern}")
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 3, max = 25, message = "{emp.lastName.Size}")
	@Pattern(regexp = "[A-Za-z0-9-]*", message = "{emp.lastName.Pattern}")
	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "title_id")
	private Title title;

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
	 * Sets first name.
	 * 
	 * @param firstName
	 *            the first name
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * Gets first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {

		return this.firstName;
	}

	/**
	 * Sets last name.
	 * 
	 * @param lastName
	 *            the last name
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	/**
	 * Gets last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {

		return this.lastName;
	}

	/**
	 * Sets department.
	 * 
	 * @param department
	 *            the department
	 */
	public void setDepartment(Department department) {

		this.department = department;
	}

	/**
	 * Gets department.
	 * 
	 * @return the department
	 */
	@XmlTransient
	public Department getDepartment() {

		return this.department;
	}

	/**
	 * Sets title.
	 * 
	 * @param title
	 *            the title
	 */
	public void setTitle(Title title) {

		this.title = title;
	}

	/**
	 * Gets title.
	 * 
	 * @return the title
	 */
	public Title getTitle() {

		return this.title;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param other
	 *            other object
	 * @return the result
	 */
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}
		if (!(other instanceof Employee)) {
			return false;
		}
		final Employee employee = (Employee) other;
		if (employee.getId() == null || !employee.getId().equals(this.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return the hash code
	 */
	public int hashCode() {

		int result = 0;
		if (this.getId() != null) {
			result = 29 * this.getId().intValue();
		}
		return result;
	}

}
