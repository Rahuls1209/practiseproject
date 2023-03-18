package com.truemeds.truemeds.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	  Optional<User> findByUsername(String userName);

	 Boolean existsByUsername(String userName);

	  Boolean existsByEmail(String email);
}
