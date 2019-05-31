package kp.company.ws.xml;

import static kp.company.util.Constants.LIKE_WILDCARD;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import kp.company.domain.Department;
import kp.company.domain.Department_;
import kp.company.domain.Employee;
import kp.company.domain.Employee_;
import kp.company.ws.WSResponseWrapper;

/**
 * Business Logic for XML Web Services.
 * 
 */
@Stateless
public class XmlBusinessLogic {

	@Inject
	private EntityManager entityManager;

	/**
	 * Lookup employees by names.
	 * 
	 * @param department
	 *            the department
	 * @param employee
	 *            the employee
	 * @return the response wrapper
	 */
	public List<WSResponseWrapper> lookupEmployees(Department department, Employee employee) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);

		Root<Employee> employeeRoot = criteria.from(Employee.class);
		Expression<String> lastNameExpr = employeeRoot.get(Employee_.lastName);
		Expression<String> nameExpr = employeeRoot.get(Employee_.department).get(Department_.name);

		Predicate predicate = cb.and(cb.like(nameExpr, String.format(LIKE_WILDCARD, department.getName())),
				cb.like(lastNameExpr, String.format(LIKE_WILDCARD, employee.getLastName())));
		criteria.where(predicate);
		criteria.select(employeeRoot);
		criteria.orderBy(cb.asc(lastNameExpr), cb.asc(nameExpr));

		TypedQuery<Employee> typedQuery = entityManager.createQuery(criteria);
		Stream<Employee> employeesStream = typedQuery.getResultList().stream();
		List<WSResponseWrapper> responseWrapperList = employeesStream.map(emp -> new WSResponseWrapper(emp))
				.collect(Collectors.toList());
		return responseWrapperList;
	}
}
