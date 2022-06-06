package com.cdcone.recipy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.repository.RoleDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleRepository;

    public String add(String name){
        try{
            RoleEntity entity = new RoleEntity();
            entity.setName(name);

            roleRepository.save(entity);
            return "success: data saved";
        } catch(Exception e){
            e.printStackTrace();
            return "failed: ";
        }
    }

    public List<RoleEntity> getAll(){
        return roleRepository.findAll();
    }
}
