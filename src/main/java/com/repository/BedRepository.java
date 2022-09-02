package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bean.BedBean;
import com.bean.CityBean;

public interface BedRepository extends CrudRepository<BedBean, UUID>{

	List<BedBean> findAll();

	BedBean findByBedId(UUID bedId);
}
