package com.thea.user;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<SysUser, Long> {

	SysUser findByName(String name);
	SysUser save(SysUser user);
}
