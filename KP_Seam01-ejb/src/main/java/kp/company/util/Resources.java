package kp.company.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager entityManager;
 * </pre>
 */
public class Resources {
	@Produces
	@PersistenceContext(unitName = "kp_seam01")
	private EntityManager entityManager;

	/**
	 * Exposes a JDK logger for injection.
	 * 
	 * @param injectionPoint
	 *            the injection point
	 * @return the logger
	 */
	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {

		String loggerName = injectionPoint.getMember().getDeclaringClass().getName();
		Logger logger = Logger.getLogger(loggerName);
		return logger;
	}
}
