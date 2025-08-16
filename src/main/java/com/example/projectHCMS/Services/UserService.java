package com.example.projectHCMS.Services;
 
import java.util.Optional;

import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.Entities.User.ROLE;
 
public interface UserService {
	User addUser(User user);
	Optional<User> getUserProfile(Long userId);
	Optional<User> loginUser(String username, String rawPassword, ROLE role);
}