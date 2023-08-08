package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.entity.Role;
import com.hrsol.helper.repository.RoleRepository;
import com.hrsol.helper.service.impl.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hrsol.helper.entity.enums.UserRole.ADMIN;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    private RoleService roleService;
    private Role role;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
        role = DummyObjects.getRole();
    }

    @Test
    void findByRoleName() {
        // given
        when(roleRepository.findByName(ADMIN.name())).thenReturn(role);

        // when
        Role roleActual = roleService.findByRoleName(ADMIN.name());

        // then
        Assertions.assertEquals(role, roleActual);
    }
}