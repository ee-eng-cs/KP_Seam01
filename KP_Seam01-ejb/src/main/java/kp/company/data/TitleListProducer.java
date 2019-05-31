package kp.company.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import kp.company.domain.Title;
import kp.company.domain.Title_;

/**
 * Title list producer.
 */
@SessionScoped
public class TitleListProducer implements Serializable {
	@Inject
	private Logger log;

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	private List<Title> titles;

	/**
	 * Titles getter.
	 * 
	 * @return the titles list
	 */
	@Produces
	@Named
	public List<Title> getTitles() {
		return titles;
	}

	/**
	 * Loads titles.
	 */
	@PostConstruct
	private void loadTitles() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Title> criteria = cb.createQuery(Title.class);
		Root<Title> titleRoot = criteria.from(Title.class);
		/*
		 * type-safe criteria query with entity’s metamodel class
		 */
		Order order = cb.asc(titleRoot.get(Title_.name));
		criteria.select(titleRoot).orderBy(order);
		titles = entityManager.createQuery(criteria).getResultList();
		log.info("loadTitles(): titles size[" + titles.size() + "]");
	}
}
