package com.example.projectHCMS.Repositories;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectHCMS.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserId(Long userId);
	List<User> findByRole(User.ROLE role);

}