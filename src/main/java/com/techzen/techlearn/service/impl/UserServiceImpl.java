package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.UserRequestDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.UserResponseDTO;
import com.techzen.techlearn.entity.UserEntity;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.UserMapper;
import com.techzen.techlearn.repository.UserRepository;
import com.techzen.techlearn.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponseDTO getUserById(UUID id) {
        UserEntity user = userRepository.findUserById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO request) {
        UserEntity user = userMapper.toUserEntity(request);
        user.setIsDeleted(false);
        user = userRepository.save(user);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO request) {
        UserEntity user = userRepository.findById(id).map(existingUser -> {
            var user1 = userMapper.toUserEntity(request);
            existingUser.setFullName(user1.getFullName());
            existingUser.setAge(user1.getAge());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public void deleteUser(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDeleted(false);
        userRepository.save(user);
    }

    @Override
    public PageResponse<?> getAllUser(int page, int pageSize) {
        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserResponseDTO> users = userRepository.findAllUser(pageable);

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .items(users.getContent())
                .build();
    }
}
