package kp.company.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel class that represents the entity 'Title'.
 * 
 */
@StaticMetamodel(Title.class)
public class Title_ {
	public static volatile SingularAttribute<Title, Long> id;
	public static volatile SingularAttribute<Title, String> name;
}
