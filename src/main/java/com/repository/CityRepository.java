package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.CityBean;

@Repository
public interface CityRepository extends CrudRepository<CityBean, UUID> {
	List<CityBean> findAll();
	
	@Query(value = "select * from city where state_id = :stateId", nativeQuery = true)
	List<CityView> findByState(UUID stateId);

	@Query(value = "select * from city where state_id = :stateId", nativeQuery = true)
	List<CityBean> findAllById(UUID stateId);
	
	

}
