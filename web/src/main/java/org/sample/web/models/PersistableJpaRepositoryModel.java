package org.sample.web.models;

import org.apache.wicket.model.LoadableDetachableModel;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mpostelnicu
 * 
 */
public class PersistableJpaRepositoryModel<T extends AbstractPersistable<Long>> extends LoadableDetachableModel<T> {

	private static final long serialVersionUID = 1L;
	private Long id;
    private JpaRepository<T, Long> jpaRepository;
 
    public PersistableJpaRepositoryModel(final Long id, final JpaRepository<T, Long> jpaRepository) {
        super();
        this.id = id;
        this.jpaRepository = jpaRepository;
    }
 
    public PersistableJpaRepositoryModel(final T t, final JpaRepository<T, Long> jpaRepository) {
        super(t);
        this.id = t.getId();
        this.jpaRepository = jpaRepository;
    }
 
    @Override
    protected T load() {
        return jpaRepository.findOne(id);
    }
   
    
    @Override
    public int hashCode()
    {
        return Long.valueOf(id).hashCode();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(final Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (obj instanceof PersistableJpaRepositoryModel)
        {
        	PersistableJpaRepositoryModel<T> other = (PersistableJpaRepositoryModel<T>)obj;
            return other.id == id;
        }
        return false;
    }
}