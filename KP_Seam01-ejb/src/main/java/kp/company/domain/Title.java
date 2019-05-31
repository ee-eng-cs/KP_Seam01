package kp.company.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Simple JavaBean business object representing a job title. The 'equals()' and
 * 'hashCode()' methods are overridden because instances of subclasses are in
 * Sets.
 */
@Entity
public class Title {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 3, max = 25, message = "{title.name.Size}")
	@Pattern(regexp = "[A-Za-z ]*", message = "{title.name.Pattern}")
	private String name;

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
		if (!(other instanceof Title)) {
			return false;
		}
		final Title title = (Title) other;
		if (title.getId() == null || !title.getId().equals(this.getId())) {
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
