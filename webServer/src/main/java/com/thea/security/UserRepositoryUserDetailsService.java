package com.thea.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thea.user.UserRepository;
import com.thea.user.SysUser;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysuser = this.userRepository.findByUsername(username);
		if (sysuser == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new User(sysuser.getUsername(), sysuser.getPassword(),
				AuthorityUtils.createAuthorityList(sysuser.getRoleArr()));
	}

}
