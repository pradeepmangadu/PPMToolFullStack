package com.hr.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hr.ppmtool.domain.User;

@Repository
public interface UserRepository extends CrudRepository<Long, User> {

}
