package org.sample.web.components.providers.textchoice;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.wicket.model.IModel;
import org.sample.web.app.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vaynberg.wicket.select2.Response;
import com.vaynberg.wicket.select2.TextChoiceProvider;

/**
 * @author mpostelnicu
 *
 */
public abstract class AbstractJpaRepositoryTextChoiceProvider<T extends AbstractPersistable<Long>>
		extends TextChoiceProvider<T> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AbstractJpaRepositoryTextChoiceProvider.class);

	
	protected Sort sort;

	@Override
	public Object getId(T choice) {
		return choice.getId();
	}
	
	protected IModel<Collection<T>> restrictedToItemsModel;
	
	protected JpaRepository<T, Long> jpaRepository;
	
	public AbstractJpaRepositoryTextChoiceProvider(JpaRepository<T, Long> jpaRepository) {
		this.jpaRepository=jpaRepository;
	}
	
	public AbstractJpaRepositoryTextChoiceProvider(JpaRepository<T, Long> jpaRepository,IModel<Collection<T>> restrictedToItemsModel) {
		this(jpaRepository);
		this.restrictedToItemsModel=restrictedToItemsModel;
	}
	

	public JpaRepository<T, Long> getJpaRepository() {
		return jpaRepository;
	}

	@Override
	public void query(String term, int page, Response<T> response) {
		PageRequest pageRequest = new PageRequest(page, WebConstants.PAGE_SIZE,sort);
			Page<T> all = findAll(pageRequest);
			if(all!=null) {
				response.addAll(all.getContent());
				response.setHasMore(all.hasNext());
			}
			
	}
	
	
	@SuppressWarnings("unchecked")
	public Page<T> findAll(Pageable pageable) {
		return getJpaRepository().findAll(pageable);
	}

	

	@Override
	public Collection<T> toChoices(Collection<String> ids) {
		ArrayList<T> r = new ArrayList<>();
		for (String s : ids) {
			Long id = Long.parseLong(s);
			T findOne = getJpaRepository().findOne(id);
			if (findOne == null)
				log.error("Cannot find entity with id=" + id
						+ " in repository " + getJpaRepository().getClass());
			else
				r.add(findOne);
		}
		return r;
	}

}
