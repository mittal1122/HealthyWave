package com.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bean.SlotBean;

public interface SlotRepository extends CrudRepository<SlotBean, UUID>{

}
