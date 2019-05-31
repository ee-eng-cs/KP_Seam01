package kp.company.controller;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * A qualifier annotation for a department selected from departments list.
 * Object with the SelectedDepartment qualifier is produced by the
 * DepartmentManagedBean and injected to the EmployeeListProducer and to the
 * EmployeeManagedBean.
 */
@Documented
@Retention(RUNTIME)
@Qualifier
public @interface SelectedDepartment {
}