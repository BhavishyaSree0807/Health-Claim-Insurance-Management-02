package com.example.HealthInsuranceClaim_MS;

import java.util.List;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.model.User;
import com.example.model.User.ROLE;

public class UserDeclaration {

	private static UserDao userdao = new UserDaoImpl();
	private static User user = new User();

	public static void createUser() {
		user.fillUserDetails("Harsha", "Harsha@123", ROLE.POLICYHOLDER, "Harsha@gmail.com");
		userdao.addUser(user);
		System.out.println("User created successfully");

	}

	public static void printUser() {
		user = userdao.getUser(1);
		user.printUserDetails();

	}

	public static void printAllUsers() {
		List<User> users = userdao.getAllUser();
		for (int i = 0; i < users.size(); i++) {
			users.get(i).printUserDetails();
		}
	}

	public static void updateUser() {
		user.fillUserDetails("Harsha", "Harsha@12", ROLE.POLICYHOLDER, "Harsha123@gmail.com");
		user.setUserId(2);
		userdao.updateUser(user);
		System.out.println("User with " + user.getUserId() + " Updated Successfully");

	}
	
	public static void deleteUser() {
		int userId = 2;
		userdao.deleteUser(userId);
		System.out.println("User with id: "+userId+" Deleted Successfully");
	}

}