/**
 * 
 */
package org.sample.web.components.providers.textchoice;

import java.util.Collection;

import org.apache.wicket.model.IModel;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mpostelnicu
 *
 */
public class StringJpaTextChoiceProvider<T extends AbstractPersistable<Long>> extends
		AbstractJpaRepositoryTextChoiceProvider<T> {
	
	
	public StringJpaTextChoiceProvider(JpaRepository<T, Long> jpaRepository) {
		super(jpaRepository);
	}

	public StringJpaTextChoiceProvider(JpaRepository<T, Long> jpaRepository,IModel<Collection<T>> restrictedToItemsModel) {
		super(jpaRepository,restrictedToItemsModel);
	}

	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see com.vaynberg.wicket.select2.TextChoiceProvider#getDisplayText(java.lang.Object)
	 */
	@Override
	public String getDisplayText(T choice) {
		return choice.toString();
	}

}
