package org.sample.persistence.repository;

import java.util.List;

import org.sample.persistence.dao.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mpostelnicu
 * 
 */
@Transactional
public interface DummyRepository extends JpaRepository<Dummy, Long> {

	@Query("select c from Dummy c where c.name = ?1")
	List<Dummy> findByName(String name);

}