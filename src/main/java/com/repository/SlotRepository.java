package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bean.SlotBean;

public interface SlotRepository extends CrudRepository<SlotBean, UUID>{
//	@Query(value="select * from where ")
//	List<SlotBean> findAllById(UUID doctorId);

	@Query(value = "select * from slot where doctor_id = :doctorId and date = :date",nativeQuery = true)
	List<SlotBean> findByDoctorIdDate(UUID doctorId,String date);
}
