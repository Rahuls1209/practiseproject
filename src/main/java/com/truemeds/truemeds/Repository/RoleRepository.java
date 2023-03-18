package com.truemeds.truemeds.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.Role;
import com.truemeds.truemeds.models.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>  {
	
	Optional<Role> findByName(ERole name);

}
