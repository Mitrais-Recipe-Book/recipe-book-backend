package com.cdcone.recipy.user.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public String add(String name) {
        try {
            RoleEntity entity = new RoleEntity();
            entity.setName(name);

            roleRepository.save(entity);
            return "success: data saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed: ";
        }
    }

    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    public Pair<String, RoleEntity> getByName(String name) {
        Optional<RoleEntity> result = roleRepository.findByName(name);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("role " + name);
        }

        return Pair.of("success: data retrieved", result.get());
    }

    public RoleEntity findByName(String name) throws EntityNotFoundException {
        Optional<RoleEntity> result = roleRepository.findByName(name);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("role " + name);
        }

        return result.get();
    }

    public Set<RoleEntity> findByUserId(long userId) throws EntityNotFoundException {
        Set<RoleEntity> result = roleRepository.findByUserId(userId);

        if (result.isEmpty()) {
            throw new EntityNotFoundException(userId + " role ");
        }

        return result;
    }
}
