package com.thea.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ActiveWebSocketUserRepository extends CrudRepository<ActiveWebSocketUser, String> {

	@Query("select DISTINCT(u.username) from ActiveWebSocketUser u where u.username != ?#{principal?.username}")
	List<String> findAllActiveUsers();

	@Query("select DISTINCT(u.id) from ActiveWebSocketUser u where u.id = {id}")
	ActiveWebSocketUser findById(String id);

}