package kp.company.data;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import kp.company.controller.SelectedDepartment;
import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Employee_;

/**
 * Employee list producer.
 */
@RequestScoped
@Named
public class EmployeeListProducer {
	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	private List<Employee> employees;

	@Inject
	@SelectedDepartment
	private Department department;

	/**
	 * Employees getter.
	 * 
	 * @return the employees list
	 */
	@Produces
	@Named
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * Loads employees.
	 */
	@PostConstruct
	private void loadEmployees() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
		Root<Employee> employeeRoot = criteria.from(Employee.class);
		/*
		 * type-safe criteria query with entity’s metamodel class
		 */
		Predicate predicate = cb.equal(employeeRoot.get(Employee_.department), department);
		Order order = cb.asc(employeeRoot.get(Employee_.lastName));
		criteria.select(employeeRoot).where(predicate).orderBy(order);
		employees = entityManager.createQuery(criteria).getResultList();
		log.info("loadEmployees(): employees size[" + employees.size() + "], department[" + department.getId() + "]");
	}
}
