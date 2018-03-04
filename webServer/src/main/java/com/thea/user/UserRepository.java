package com.thea.user;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<SysUser, Long> {

	SysUser findByUsername(String name);

	SysUser save(SysUser user);
}
