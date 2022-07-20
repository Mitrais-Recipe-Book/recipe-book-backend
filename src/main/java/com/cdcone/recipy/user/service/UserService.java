package com.cdcone.recipy.user.service;

import com.cdcone.recipy.user.dto.repository.FollowerDto;
import com.cdcone.recipy.recipe.dto.response.FollowingListResponseDto;
import com.cdcone.recipy.dto.response.PhotoResponseDto;
import com.cdcone.recipy.dto.response.PaginatedDto;
import com.cdcone.recipy.user.dto.request.ChangePasswordRequestDto;
import com.cdcone.recipy.user.dto.request.SignUpRequestDto;
import com.cdcone.recipy.user.dto.request.UpdateUserRequestDto;
import com.cdcone.recipy.user.dto.repository.UserProfile;
import com.cdcone.recipy.user.dto.response.UserResponseDto;
import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.entity.UserEntity;
import com.cdcone.recipy.recipe.repository.RecipeRepository;
import com.cdcone.recipy.recipe.service.RecipeReactionService;
import com.cdcone.recipy.recipe.service.RecipeService;
import com.cdcone.recipy.error.PasswordNotMatchException;
import com.cdcone.recipy.user.repository.RoleRepository;
import com.cdcone.recipy.user.repository.UserRepository;
import com.cdcone.recipy.security.CustomUser;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final RecipeRepository recipeRepo;
    private final RecipeReactionService recipeReactionService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserEntity userEntity = byUsername.get();
        List<SimpleGrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(it -> new SimpleGrantedAuthority(it.getName()))
                .collect(Collectors.toList());
        return new CustomUser(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFullName(),
                authorities);
    }

    public Pair<Optional<UserResponseDto>, String> addUser(SignUpRequestDto signUpRequestDto) {
        if (signUpRequestDto.checkBlank()) {
            return Pair.of(Optional.empty(), "Please fill out all required fields.");
        }

        String msg;
        UserResponseDto createdUser = null;

        if (signUpRequestDto.getPassword().length() < 8) {
            msg = "Password must be equal or more than 8 characters";
        } else {
            Optional<RoleEntity> userRole = roleRepository.findByName("User");
            if (userRole.isPresent()) {
                try {
                    UserEntity user = new UserEntity();
                    user.setEmail(signUpRequestDto.getEmail().toLowerCase());
                    user.setUsername(signUpRequestDto.getUsername().toLowerCase());
                    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
                    user.setRoles(Set.of(userRole.get()));
                    user.setFullName(signUpRequestDto.getFullName());
                    createdUser = UserResponseDto.toDto(userRepository.save(user));
                    msg = "Success";
                } catch (DataIntegrityViolationException e) {
                    msg = "Failed to create user. Username or email is already exists";
                }
            } else {
                msg = "Role User not found";
            }
        }
        return Pair.of(Optional.ofNullable(createdUser), msg);
    }

    public UserEntity getById(long id) {
        return userRepository.getById(id);
    }

    public Pair<String, UserEntity> getByUsername(String username) {
        Optional<UserEntity> result = userRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw new EntityNotFoundException(username);
        }

        return Pair.of("success: user found", result.get());
    }

    public Optional<UserProfile> findByUsername(String username) {
        Optional<UserProfile> userProfile = userRepository.findDetailByUsername(username);
        userProfile.ifPresent(it -> {
            int recipeLikes = recipeReactionService.getTotalRecipeLikeByUserId(it.getId());
            Set<RoleEntity> roles = roleRepository.findByUserId(it.getId());
            Long followerCount = userRepository.getFollowerCountById(it.getId());
            Integer recipeCount = recipeRepo.getTotalRecipeByUsername(username);
            it.setRoles(roles);
            it.setFollowers(followerCount);
            it.setRecipeLikes(recipeLikes);
            it.setTotalRecipes(recipeCount);
        });
        return userProfile;
    }

    public PhotoResponseDto getUserPhoto(String username) {
        return userRepository.getProfilePhoto(username);
    }

    public Pair<Boolean, String> saveProfilePhoto(MultipartFile photo, String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        String msg = "User not found.";
        boolean uploadedPhoto = false;
        if (byUsername.isPresent()) {
            try {
                BufferedImage original = ImageIO.read(photo.getInputStream());
                if (original != null) {
                    byte[] resizedPhoto = resizePhoto(original);
                    UserEntity user = byUsername.get();
                    user.setProfilePhoto(resizedPhoto);
                    user.setProfilePhotoType("image/jpeg");
                    userRepository.save(user);
                    msg = "Success";
                    uploadedPhoto = true;
                } else {
                    msg = "Uploaded file is not an image";
                }
            } catch (IOException e) {
                msg = "Failed to save profile photo.";
            }
        }
        return Pair.of(uploadedPhoto, msg);
    }

    private byte[] resizePhoto(BufferedImage original) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage newImage = new BufferedImage(
                original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(original, 0, 0, Color.WHITE, null);
        ImageIO.write(newImage, "jpg", outputStream);
        graphics.dispose();
        return outputStream.toByteArray();
    }

    public PaginatedDto<UserResponseDto> getAllUsers(boolean isPaginated, int page, int size) {
        Pageable pageable = isPaginated ? PageRequest.of(page, size) : Pageable.unpaged();

        Page<UserEntity> allPaged = userRepository.findAllPaged(pageable);
        List<UserResponseDto> userResponseDtoList = allPaged.getContent()
                .stream()
                .map(UserResponseDto::toDto)
                .collect(Collectors.toList());
        return new PaginatedDto<>(userResponseDtoList,
                allPaged.getNumber(),
                allPaged.getTotalPages(),
                allPaged.isLast(),
                allPaged.getTotalElements());
    }

    public void addFollow(long userId, long creatorId) throws Exception {
        if (userId == creatorId) {
            throw new Exception("Cannot follow yourself");
        }

        UserEntity user = userRepository.findById(userId).get();
        UserEntity creator = userRepository.findById(creatorId).get();

        Set<UserEntity> follows = user.getFollows();

        if (follows.contains(creator)) {
            throw new Exception("You already follow this creator");
        }

        follows.add(creator);
        user.setFollows(follows);
        userRepository.save(user);
    }

    public void unFollow(long userId, long creatorId) throws Exception {
        if (userId == creatorId) {
            throw new Exception("Cannot follow yourself");
        }

        UserEntity user = userRepository.findById(userId).get();
        UserEntity creator = userRepository.findById(creatorId).get();

        Set<UserEntity> follows = user.getFollows();

        if (!follows.contains(creator)) {
            throw new Exception("You didn't follow this creator");
        }

        follows.remove(creator);
        user.setFollows(follows);
        userRepository.save(user);
    }

    public List<FollowingListResponseDto> getFollowList(long userId) {
        List<UserEntity> entities = new ArrayList<>(userRepository.findById(userId).get().getFollows());
        return entities.stream()
                .map(entity -> new FollowingListResponseDto(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getFullName()))
                .collect(Collectors.toList());
    }

    public List<FollowerDto> getFollowerList(long userId) {
        return userRepository.getFollowersById(userId);
    }

    public Long getFollowerCountById(long userId) {
        return userRepository.getFollowerCountById(userId);
    }

    public Boolean isFollowing(Long creatorId, Long userId) {
        return userRepository.isFollowing(creatorId, userId);
    }

    public UserEntity assignRole(String username, String rolename) {
        Pair<String, RoleEntity> role = roleService.getByName(rolename);
        Pair<String, UserEntity> user = getByUsername(username);

        user.getSecond().getRoles().add(role.getSecond());
        return userRepository.save(user.getSecond());
    }

    public UserEntity removeRole(String username, String rolename) {
        Pair<String, RoleEntity> role = roleService.getByName(rolename);
        Pair<String, UserEntity> user = getByUsername(username);

        user.getSecond().getRoles().remove(role.getSecond());
        return userRepository.save(user.getSecond());
    }

    public List<UserEntity> getUsersWithRoleRequest() {
        String rolename = "Request";

        return userRepository.getUsersWithRole(rolename);
    }

    public Pair<HttpStatus, Optional<UserResponseDto>> updateUser(String username, UpdateUserRequestDto updateUserDto) {
        UserResponseDto result = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        Pair<String, UserEntity> byUsername = getByUsername(username);

        if (byUsername.getFirst().charAt(0) == 's') {
            try {
                UserEntity user = byUsername.getSecond();
                user.setEmail(updateUserDto.getEmail().toLowerCase());
                user.setFullName(updateUserDto.getFullName().toLowerCase());
                userRepository.save(user);
                result = UserResponseDto.toDto(user);
                status = HttpStatus.OK;
            } catch (DataIntegrityViolationException e) {
                status = HttpStatus.BAD_REQUEST;
            }
        }

        return Pair.of(status, Optional.ofNullable(result));
    }

    public UserResponseDto changePassword(
            String username,
            ChangePasswordRequestDto request) {
        Pair<String, UserEntity> byUsername = getByUsername(username);

        UserEntity user = byUsername.getSecond();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())
                || !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordNotMatchException();
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return UserResponseDto.toDto(user);
    }
}
