package com.cdcone.recipy.service;

import com.cdcone.recipy.dto.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public Pair<Optional<UserEntity>, String> addUser(SignUpDto signUpDto) {
        String msg;
        UserEntity createdUser = null;

        if (signUpDto.getPassword().length() < 6) {
            msg = "Password must be equal or more than 6 characters";
        } else {
            Optional<RoleEntity> userRole = roleDao.findByName("User");
            if (userRole.isPresent()) {
                try {
                    UserEntity user = new UserEntity();
                    user.setEmail(signUpDto.getEmail());
                    user.setUsername(signUpDto.getUsername());
                    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
                    user.setRoles(Set.of(userRole.get()));
                    user.setFullName(signUpDto.getFullName());
                    createdUser = userDao.save(user);
                    msg = "Success";
                } catch (Exception e) {
                    msg = "Failed to create user. Username or email is already exist";
                }
            } else {
                msg = "Role not found";
            }
        }
        return Pair.of(Optional.ofNullable(createdUser), msg);
    }

    public UserEntity getById(long id){
        return userDao.getById(id);
    }
}
