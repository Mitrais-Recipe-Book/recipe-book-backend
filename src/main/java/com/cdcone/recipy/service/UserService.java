package com.cdcone.recipy.service;

import com.cdcone.recipy.dtoAccess.PhotoDto;
import com.cdcone.recipy.dtoAccess.UserDetailDto;
import com.cdcone.recipy.dtoAccess.UserDto;
import com.cdcone.recipy.dtoRequest.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.entity.UserEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byUsername = userDao.findByUsername(username);
        if (byUsername.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserEntity userEntity = byUsername.get();
        List<SimpleGrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(it -> new SimpleGrantedAuthority(it.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

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
                    user.setEmail(signUpDto.getEmail().toLowerCase());
                    user.setUsername(signUpDto.getUsername().toLowerCase());
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

    public Optional<UserDetailDto> findByUsername(String username) {
        return userDao.findDetailByUsername(username);
    }

    public PhotoDto getUserPhoto(String username) {
        return userDao.getProfilePhoto(username);
    }

    public Pair<Boolean, String> saveProfilePhoto(MultipartFile photo, String username) {
        Optional<UserEntity> byUsername = userDao.findByUsername(username);
        String msg = "User not found.";
        boolean uploadedPhoto = false;
        if (byUsername.isPresent()) {
            try {
                UserEntity user = byUsername.get();
                user.setProfilePhoto(photo.getBytes());
                user.setProfilePhotoType(photo.getContentType());
                userDao.save(user);
                msg = "Success";
                uploadedPhoto = true;
            } catch (IOException e) {
                msg = "Failed to save profile photo.";
            }
        }
        return Pair.of(uploadedPhoto, msg);
    }

    public Page<UserDto> getAllUsers(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userDao.findAllPaged(pageable);
    }

    public void addFollow(long userId, long creatorId){
        UserEntity user = userDao.findById(userId).get();
        UserEntity creator = userDao.findById(creatorId).get();

        user.addFollow(creator);
        userDao.save(user);
    }

    public void removeFollow(long userId, long creatorId){
        UserEntity user = userDao.findById(userId).get();
        UserEntity creator = userDao.findById(creatorId).get();

        user.removeFollow(creator);
        userDao.save(user);
    }
}
