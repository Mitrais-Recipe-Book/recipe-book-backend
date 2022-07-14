package com.cdcone.recipy.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.repository.RoleDao;

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

    public Pair<String, RoleEntity> getByName(String name){
        Optional<RoleEntity> result = roleRepository.findByName(name);

        if (result.isEmpty()){
            return Pair.of("failed: cannot find role with name " + name, new RoleEntity());
        }

        return Pair.of("success: data retrieved", result.get());
    }
}
