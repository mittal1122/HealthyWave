package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bean.SymptomBean;

public interface SymptomRepository extends CrudRepository<SymptomBean, UUID>{

	List<SymptomBean> findAll();
	
	SymptomBean findBySymptomId(UUID symptomId);
}
