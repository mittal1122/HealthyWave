package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.StaffBean;

@Repository
public interface StaffRepository extends CrudRepository<StaffBean, UUID>{
	List<StaffBean> findAll();
}
