package com.repository;

import java.util.List;
import java.util.UUID;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.WardBean;

@Repository
public interface wardRepository extends CrudRepository<WardBean, UUID>{

	List<WardBean> findAll();
	
	WardBean findByWardId(UUID wardId);
}
