package com.hr.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hr.ppmtool.domain.Product;

@Repository
public interface ProjectRepository extends CrudRepository<Product, Long>{

	@Override
	default Iterable<Product> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
