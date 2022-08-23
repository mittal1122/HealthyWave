package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.SpecializationBean;

@Repository
public interface SpecializationRepository extends CrudRepository<SpecializationBean, UUID>{
	List<SpecializationBean> findAll();
	SpecializationBean findBySpecializationId(UUID specializationId);
}
