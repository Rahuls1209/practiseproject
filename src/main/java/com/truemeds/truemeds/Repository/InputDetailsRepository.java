package com.truemeds.truemeds.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.InputDetail;

@Repository
public interface InputDetailsRepository extends JpaRepository<InputDetail,Long> {
	
	

}
