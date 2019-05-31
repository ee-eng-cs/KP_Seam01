package kp.company.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel class that represents the entity 'Department'.
 * 
 * The metamodel class and its attributes are used in Criteria queries to refer
 * to the managed entity classes and their persistent state and relationships.
 * 
 */
@StaticMetamodel(Department.class)
public class Department_ {
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile ListAttribute<Department, Employee> employees;
}
