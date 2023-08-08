package com.hrsol.helper.service.impl;

import com.hrsol.helper.entity.Role;
import com.hrsol.helper.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRoleName(String role) {
        return roleRepository.findByName(role);
    }
}
