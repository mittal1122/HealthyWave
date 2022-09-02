package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bean.StaffBean;

public interface StaffRepository extends CrudRepository<StaffBean,UUID> {

	List<StaffBean> findAll();
}
