package org.sample.persistence.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class CategoryDummy extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private Set<Dummy> dummy = new HashSet<Dummy>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the dummy
	 */
	public Set<Dummy> getDummy() {
		return dummy;
	}

	/**
	 * @param dummy the dummy to set
	 */
	public void setDummy(Set<Dummy> dummy) {
		this.dummy = dummy;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
