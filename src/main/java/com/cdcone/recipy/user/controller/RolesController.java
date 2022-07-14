package com.cdcone.recipy.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.user.service.RoleService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/roles")
public class RolesController {

    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse> add(@RequestBody String name) {
        String result = roleService.add(name);

        if (result.charAt(0) == 's') {
            return ResponseEntity.ok().body(new CommonResponse(result));
        }
        return ResponseEntity.badRequest().body(new CommonResponse(result));
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.ok()
                .body(new CommonResponse("success", roleService.getAll()));
    }
}
