package com.example.projectHCMS.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.Entities.User.ROLE;
import com.example.projectHCMS.Repositories.UserRepository;
import com.example.projectHCMS.Services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public boolean isUserNameTaken(String userName) {
		return userRepository.findByUserName(userName).isPresent();
	}

	@Override
	public Optional<User> getUserProfile(Long userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public Optional<User> loginUser(String username, String rawPassword, ROLE role) {
		Optional<User> userOpt = userRepository.findByUserName(username);
		if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())
				&& role.equals(userOpt.get().getRole())) {
			return userOpt;
		}
		return Optional.empty();
	}

}