package com.cdcone.recipy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.cdcone.recipy.user.controller.RolesController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.service.RoleService;

public class RoleControllerTest {

    private static RolesController roleController;

    private static final RoleService roleService = mock(RoleService.class);

    @BeforeAll
    static void init() {
        roleController = new RolesController(roleService);
    }

    @Test
    void add() {
        when(roleService.add("any"))
                .thenReturn("success");

        assertEquals(HttpStatus.OK,
                roleController.add("any").getStatusCode());
    }

    @Test
    void failAdd() {
        when(roleService.add("any"))
                .thenReturn("failed");

        assertNotEquals(HttpStatus.OK,
                roleController.add("any").getStatusCode());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAll() {
        List<RoleEntity> mockResult = mock(List.class);
        when(roleService.getAll()).thenReturn(mockResult);

        assertEquals(HttpStatus.OK, roleController.getAll().getStatusCode());
    }
}
