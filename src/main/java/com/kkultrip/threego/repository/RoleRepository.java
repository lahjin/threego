package com.kkultrip.threego.repository;

import com.kkultrip.threego.model.ERole;
import com.kkultrip.threego.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
