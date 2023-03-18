package com.truemeds.truemeds.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.InputDetail;
import com.truemeds.truemeds.models.OutputDetail;

@Repository
public interface OutputDetailsRepository extends JpaRepository<OutputDetail,Long> {

	
}
