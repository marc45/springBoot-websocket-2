package com.thea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.thea.user.SysUser;
import com.thea.user.UserRepository;

@Component
public class AuthUserLoader implements CommandLineRunner {

	private final UserRepository sysUsers;

	@Autowired
	public AuthUserLoader(UserRepository userRepository) {

		this.sysUsers = userRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		this.sysUsers.save(new SysUser("user", "password", "ROLE_USER"));
		this.sysUsers.save(new SysUser("root", "root", "ROLE_ADMIN"));
	}
}