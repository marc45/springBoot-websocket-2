/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thea.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.thea.model.Portfolio;
import com.thea.user.SysUser;
import com.thea.user.UserRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Portfolio.class)
public class SpringDataRestEventHandler {

	private final UserRepository userRepository;

	@Autowired
	public SpringDataRestEventHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@HandleBeforeCreate
	public void applyUserInformationUsingSecurityContext(Portfolio portfolio) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUser sysUser = this.userRepository.findByName(name);
		if (sysUser == null) {
			SysUser newUser = new SysUser();
			newUser.setUsername(name);
			newUser.setRoleArr(new String[] { "ROLE_USER" });
			sysUser = this.userRepository.save(newUser);
		}
		portfolio.setUser(sysUser);
	}
}
// end::code[]
