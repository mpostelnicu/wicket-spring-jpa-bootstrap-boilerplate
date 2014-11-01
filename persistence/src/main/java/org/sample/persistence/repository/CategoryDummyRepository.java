package org.sample.persistence.repository;

import java.util.List;

import org.sample.persistence.dao.CategoryDummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mpostelnicu
 * 
 */
@Transactional
public interface CategoryDummyRepository extends JpaRepository<CategoryDummy, Long> {

	@Query("select c from CategoryDummy c where c.name = ?1")
	List<CategoryDummy> findByName(String name);

}