package com.cdcone.recipy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.cdcone.recipy.user.service.RoleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.repository.RoleDao;

public class RoleServiceTest {

    private static RoleService roleService;

    private static RoleDao roleRepository = mock(RoleDao.class);

    private static final RoleEntity ROLE_ENTITY = mock(RoleEntity.class); 

    @BeforeAll
    static void init(){
        roleService = new RoleService(roleRepository);
    }

    @Test
    void successGetRoleByName(){
        when(ROLE_ENTITY.getName()).thenReturn("User");
        when(roleRepository.findByName("User")).thenReturn(Optional.of(ROLE_ENTITY));

        assertEquals('s', roleService.getByName("User").getFirst().charAt(0));
        assertEquals(ROLE_ENTITY, roleService.getByName("User").getSecond());
    }
    
}
