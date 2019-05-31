package kp.company.ws.rest.service;

import static kp.company.util.Constants.SHOW_EMPLOYEES_1;
import static kp.company.util.Constants.SHOW_EMPLOYEES_2;
import static kp.company.util.Constants.SHOW_EMPLOYEES_3;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import kp.company.domain.Title;
import kp.company.ws.WSResponseWrapper;
import kp.company.ws.rest.RestBusinessLogic;

/**
 * Company REST Service.
 */
@Path("company")
@RequestScoped
public class CompanyRESTService {
	@Inject
	private Logger log;

	@Inject
	private RestBusinessLogic restBusinessLogic;

	@Inject
	private List<Title> titles;

	/**
	 * Lookup department by name.
	 * 
	 * @param depName
	 *            HTTP query parameter depName
	 * @return the response wrapper
	 */
	@GET
	@Path("dep")
	@Produces("text/xml")
	public List<WSResponseWrapper> lookupDepartmentByName(@QueryParam("depName") String depName) {

		List<WSResponseWrapper> responseWrapperList = restBusinessLogic.lookupDepartmentByName(depName);
		log.info("lookupDepartmentByName(): depName[" + depName + "]");
		return responseWrapperList;
	}

	/**
	 * Lookup employee by name.
	 * 
	 * @param depName
	 *            HTTP query parameter depName
	 * @param empName
	 *            HTTP query parameter empName
	 * @return the response wrapper
	 */
	@GET
	@Path("emp")
	@Produces("text/xml")
	public List<WSResponseWrapper> lookupEmployeeByName(@QueryParam("depName") String depName,
			@QueryParam("empName") String empName) {

		List<WSResponseWrapper> responseWrapperList = restBusinessLogic.lookupEmployeeByName(depName, empName);
		log.info("lookupEmployeeByName(): depName[" + depName + "], empName[" + empName + "]");
		return responseWrapperList;
	}

	/**
	 * Lookup titles list.
	 * 
	 * @return the titles list
	 */
	@GET
	@Path("titles")
	@Produces("application/json")
	public List<Title> lookupTitles() {

		log.info("lookupTitles(): titles size[" + titles.size() + "]");
		return titles;
	}

	/**
	 * Lookup title by id.
	 * 
	 * @param titleId
	 *            the URI path segment for the title ID
	 * @return the title
	 */
	@GET
	@Path("title/{titleId:[0-9][0-9]*}")
	@Produces("application/json")
	public Title lookupTitleById(@PathParam("titleId") Long titleId) {

		Predicate<Title> filter = t -> titleId.equals(t.getId());
		Title title = titles.stream().filter(filter).findFirst().orElse(null);
		return title;
	}

	/**
	 * Lookup employees grouped by the title.
	 * 
	 * @return the response
	 */
	@GET
	@Path("emp/grouped")
	@Produces("text/html")
	public String lookupEmployeesGroupedByTitle() {

		Map<String, String> resultMap = restBusinessLogic.lookupEmployeesGroupedByTitle();
		StringBuilder strBuf = new StringBuilder(SHOW_EMPLOYEES_1);
		for (String key : resultMap.keySet()) {
			strBuf.append(String.format(SHOW_EMPLOYEES_2, key, resultMap.get(key)));
		}
		strBuf.append(SHOW_EMPLOYEES_3);

		log.info("lookupEmployeesGroupedByTitle():");
		return strBuf.toString();
	}
}
