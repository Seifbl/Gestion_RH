package com.fl2.gd.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fl2.gd.Entities.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByAuthority(String authority);
}

