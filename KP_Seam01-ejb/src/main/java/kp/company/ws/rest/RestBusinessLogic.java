package kp.company.ws.rest;

import static kp.company.util.Constants.LIKE_WILDCARD;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import kp.company.domain.Department;
import kp.company.domain.Department_;
import kp.company.domain.Employee;
import kp.company.domain.Employee_;
import kp.company.domain.Title_;
import kp.company.ws.WSResponseWrapper;

/*
 * Two ways of querying: 
 * 1. querying with Java Persistence Query Language 
 * 2. querying with the Criteria API (metamodel queries or string-based queries)
 * 
 * Here are used criteria API metamodel queries.
 */
/**
 * Business Logic for RESTful Web Services.
 * 
 */
@Stateless
public class RestBusinessLogic {

	@Inject
	private EntityManager entityManager;

	/**
	 * Lookup department by name.
	 * 
	 * @param depName
	 *            the department name
	 * @return the response list wrapper
	 */
	public List<WSResponseWrapper> lookupDepartmentByName(String depName) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> criteria = cb.createQuery(Department.class);

		Root<Department> departmentRoot = criteria.from(Department.class);
		Expression<String> nameExpr = departmentRoot.get(Department_.name);

		Predicate predicate = cb.like(nameExpr, String.format(LIKE_WILDCARD, depName));
		criteria.where(predicate);
		criteria.select(departmentRoot);
		criteria.orderBy(cb.asc(nameExpr));

		TypedQuery<Department> typedQuery = entityManager.createQuery(criteria);
		Stream<Department> departmentsStream = typedQuery.getResultList().stream();
		List<WSResponseWrapper> responseWrapperList = departmentsStream.map(dep -> new WSResponseWrapper(dep))
				.collect(Collectors.toList());
		return responseWrapperList;
	}

	/**
	 * Lookup employee by names.
	 * 
	 * @param depName
	 *            the department name
	 * @param empName
	 *            the employee name
	 * @return the response list wrapper
	 */
	public List<WSResponseWrapper> lookupEmployeeByName(String depName, String empName) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);

		Root<Employee> employeeRoot = criteria.from(Employee.class);
		Expression<String> lastNameExpr = employeeRoot.get(Employee_.lastName);
		Expression<String> nameExpr = employeeRoot.get(Employee_.department).get(Department_.name);

		Predicate predicate = cb.and(cb.like(nameExpr, String.format(LIKE_WILDCARD, depName)),
				cb.like(lastNameExpr, String.format(LIKE_WILDCARD, empName)));
		criteria.where(predicate);
		criteria.select(employeeRoot);
		criteria.orderBy(cb.asc(lastNameExpr), cb.asc(nameExpr));

		TypedQuery<Employee> typedQuery = entityManager.createQuery(criteria);
		Stream<Employee> employeesStream = typedQuery.getResultList().stream();
		List<WSResponseWrapper> responseWrapperList = employeesStream.map(emp -> new WSResponseWrapper(emp))
				.collect(Collectors.toList());
		return responseWrapperList;
	}

	/**
	 * Lookup employees grouped by the title.
	 * 
	 * @return the result map
	 */
	public Map<String, String> lookupEmployeesGroupedByTitle() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = cb.createTupleQuery();

		Root<Employee> employeeRoot = criteria.from(Employee.class);
		Expression<Long> countExpr = cb.count(employeeRoot);
		javax.persistence.criteria.Path<String> titlePath = employeeRoot.get(Employee_.title).get(Title_.name);
		criteria.multiselect(titlePath, countExpr);
		criteria.groupBy(employeeRoot.get(Employee_.title));
		Order order = cb.asc(titlePath);
		criteria.orderBy(order);

		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteria);
		Stream<Tuple> resultStream = typedQuery.getResultList().stream();
		Collector<Tuple, ?, Map<String, String>> collector = Collectors.toMap(t -> t.get(0).toString(),
				t -> t.get(1).toString());
		Map<String, String> resultMap = resultStream.collect(collector);
		return resultMap;
	}

}
