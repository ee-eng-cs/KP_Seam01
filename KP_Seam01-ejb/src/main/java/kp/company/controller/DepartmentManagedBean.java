package kp.company.controller;

import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import kp.company.domain.Department;

/**
 * Department managed bean.
 */
@Stateful
@ConversationScoped
@Named
public class DepartmentManagedBean {

	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	@Inject
	Conversation conversation;

	private Department department;

	/**
	 * Selected department producer.
	 * 
	 * @return the department
	 */
	@Produces
	@SelectedDepartment
	@Named
	public Department getDepartment() {
		return department;
	}

	/**
	 * Go to the home page.
	 * 
	 * @return the outcome
	 */
	public String homeCompanyAction() {

		if (!conversation.isTransient()) {
			conversation.end();
		}
		log.info("homeCompanyAction():");
		return "homeCompany";
	}

	/**
	 * List departments.
	 * 
	 * @return the outcome
	 */
	public String listDepartmentsAction() {

		if (!conversation.isTransient()) {
			conversation.end();
		}
		log.info("listDepartmentsAction():");
		return "listDepartments";
	}

	/**
	 * Lists employees.
	 * 
	 * @param department
	 *            the department
	 * @return the outcome
	 */
	public String listEmployeesAction(Department department) {

		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.department = department;
		log.info("listEmployeesAction(): department[" + department.getId()
				+ "]");
		return "listEmployees";
	}

	/**
	 * Edits department.
	 * 
	 * @param department
	 *            the department
	 * @return the outcome
	 */
	public String editDepartmentAction(Department department) {

		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.department = department;
		log.info("editDepartmentAction():");
		return "editDepartment";
	}

	/**
	 * Updates department.
	 * 
	 * @return the outcome
	 */
	public String updateDepartmentAction() {

		department = entityManager.merge(department);
		if (!conversation.isTransient()) {
			conversation.end();
		}
		log.info("updateDepartmentAction():");
		return "listDepartments";
	}

	/**
	 * Confirms department deletion.
	 * 
	 * @param department
	 *            the department
	 * @return the outcome
	 */
	public String confirmDeleteDepartmentAction(Department department) {

		if (conversation.isTransient()) {
			conversation.begin();
		}
		this.department = department;
		log.info("confirmDeleteDepartmentAction():");
		return "confirmDeleteDepartment";
	}

	/**
	 * Deletes department.
	 * 
	 * @return the outcome
	 */
	public String deleteDepartmentAction() {

		department = entityManager.merge(department);
		entityManager.remove(department);
		department = null;
		if (!conversation.isTransient()) {
			conversation.end();
		}
		log.info("deleteDepartmentAction():");
		return "listDepartments";
	}

	/**
	 * Adds new department.
	 * 
	 * @return the outcome
	 */
	public String addDepartmentAction() {

		if (conversation.isTransient()) {
			conversation.begin();
		}
		department = new Department();
		log.info("addDepartmentAction():");
		return "editDepartment";
	}

	/**
	 * Cancels action for department.
	 * 
	 * @return the outcome
	 */
	public String cancelDepartmentAction() {

		if (!conversation.isTransient()) {
			conversation.end();
		}
		log.info("cancelDepartmentAction():");
		return "listDepartments";
	}

}
