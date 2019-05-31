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
import javax.persistence.criteria.Root;

import kp.company.domain.Department;
import kp.company.domain.Department_;

/**
 * Department list producer.
 */
@RequestScoped
@Named
public class DepartmentListProducer {
	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	private List<Department> departments;

	/*
	 * @Named provides access the return value via the EL variable name
	 * "departments" in the UI
	 */
	/**
	 * Departments getter.
	 * 
	 * @return the departments list
	 */
	@Produces
	@Named
	public List<Department> getDepartments() {
		return departments;
	}

	/**
	 * Loads departments.
	 */
	@PostConstruct
	private void loadDepartments() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> criteria = cb.createQuery(Department.class);
		Root<Department> departmentRoot = criteria.from(Department.class);
		/*
		 * type-safe criteria query with entity’s metamodel class
		 */
		Order order = cb.asc(departmentRoot.get(Department_.name));
		criteria.select(departmentRoot).orderBy(order);
		departments = entityManager.createQuery(criteria).getResultList();
		log.info("loadDepartments(): departments size[" + departments.size() + "]");
	}
}
