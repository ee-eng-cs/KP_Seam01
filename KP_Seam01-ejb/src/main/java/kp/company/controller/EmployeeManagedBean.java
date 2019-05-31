package kp.company.controller;

import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;

/**
 * Employee managed bean.
 */
@Stateful
@ConversationScoped
@Named
public class EmployeeManagedBean {

	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	private Employee employee;

	@Inject
	@SelectedDepartment
	private Department department;

	private Long selectedTitleId;

	/**
	 * Employee producer.
	 * 
	 * @return the employee
	 */
	@Produces
	@Named
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * Sets selected title id.
	 * 
	 * @param selectedTitleId
	 *            the selected title id to set
	 */
	public void setSelectedTitleId(Long selectedTitleId) {
		this.selectedTitleId = selectedTitleId;
	}

	/**
	 * Gets selected title id.
	 * 
	 * @return the selected title id
	 */
	public Long getSelectedTitleId() {

		if (employee != null && employee.getTitle() != null) {
			selectedTitleId = employee.getTitle().getId();
		} else {
			selectedTitleId = 0L;
		}
		return selectedTitleId;
	}

	/**
	 * Edits employee.
	 * 
	 * @param employee
	 *            the employee
	 * @return the outcome
	 */
	public String editEmployeeAction(Employee employee) {

		this.employee = employee;
		log.info("editEmployeeAction():");
		return "editEmployee";
	}

	/**
	 * Updates employee.
	 * 
	 * @return the outcome
	 */
	public String updateEmployeeAction() {

		if (employee.getTitle() == null
				|| !selectedTitleId.equals(employee.getTitle().getId())) {
			Title title = entityManager.find(Title.class, selectedTitleId);
			employee.setTitle(title);
		}
		if (employee.getDepartment() == null) {
			/*
			 * added new employee
			 */
			employee.setDepartment(department);
			employee = entityManager.merge(employee);
			department.addEmployee(employee);
			department = entityManager.merge(department);
		} else {
			employee = entityManager.merge(employee);
		}
		log.info("updateEmployeeAction():");
		return "listEmployees";
	}

	/**
	 * Confirms employee deletion.
	 * 
	 * @param employee
	 *            the employee
	 * @return the outcome
	 */
	public String confirmDeleteEmployeeAction(Employee employee) {

		this.employee = employee;
		log.info("confirmDeleteEmployeeAction():");
		return "confirmDeleteEmployee";
	}

	/**
	 * Deletes employee.
	 * 
	 * @return the outcome
	 */
	public String deleteEmployeeAction() {

		employee = entityManager.merge(employee);
		department.removeEmployee(employee);
		department = entityManager.merge(department);

		entityManager.remove(employee);
		employee = null;
		log.info("deleteEmployeeAction():");
		return "listEmployees";
	}

	/**
	 * Adds new employee.
	 * 
	 * @return the outcome
	 */
	public String addEmployeeAction() {

		employee = new Employee();
		log.info("addEmployeeAction():");
		return "editEmployee";
	}

	/**
	 * Cancels action for employee.
	 * 
	 * @return the outcome
	 */
	public String cancelEmployeeAction() {

		log.info("cancelEmployeeAction():");
		return "listEmployees";
	}

}
