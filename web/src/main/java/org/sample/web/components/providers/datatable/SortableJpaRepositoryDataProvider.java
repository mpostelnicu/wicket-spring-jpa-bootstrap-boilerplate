/**
 * 
 */
package org.sample.web.components.providers.datatable;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.web.app.WebConstants;
import org.sample.web.models.PersistableJpaRepositoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mpostelnicu
 * 
 * Smart generic {@link SortableDataProvider} that binds to {@link JpaRepository}
 */
public class SortableJpaRepositoryDataProvider<T extends AbstractPersistable<Long>>
		extends SortableDataProvider<T, String> {

	private static final long serialVersionUID = 1L;

	protected JpaRepository<T, Long> jpaRepository;
	
	/**
	 * Always provide a proxy jpaRepository here! For example one coming from a {@link SpringBean}
	 * 
	 * @param jpaRepository
	 */
	public SortableJpaRepositoryDataProvider(
			JpaRepository<T, Long> jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	/**
	 * Translates from a {@link SortParam} to a Spring {@link Sort}
	 * 
	 * @return
	 */
	protected Sort translateSort() {
		if(getSort()==null) return null;
		return new Sort(getSort().isAscending() ? Direction.ASC
				: Direction.DESC, getSort().getProperty());
	}

	/**
	 * @see SortableDataProvider#iterator(long, long)
	 */
	@Override
	public Iterator<? extends T> iterator(long first, long count) {
		int page = (int)((double) first /  WebConstants.PAGE_SIZE);
		Page<T> findAll = jpaRepository.findAll(
				new PageRequest((int) page, WebConstants.PAGE_SIZE, translateSort()));
		return findAll.iterator();
	}

	@Override
	public long size() {
		return jpaRepository.count();
	}

	/**
	 * This ensures that the object is detached and reloaded after
	 * deserialization of the page, since the
	 * {@link PersistableJpaRepositoryModel} is also loadabledetachable
	 */
	@Override
	public IModel<T> model(T object) {
		return new PersistableJpaRepositoryModel<T>(object, jpaRepository);
	}

}
