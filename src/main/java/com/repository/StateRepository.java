package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.StateBean;
@Repository
public interface StateRepository extends CrudRepository<StateBean, UUID>{

	List<StateBean> findAll();
	StateBean findByStateId(UUID stateId);
	
}
