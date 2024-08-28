package com.techzen.techlearn.repository;

import com.techzen.techlearn.dto.response.UserResponseDTO;
import com.techzen.techlearn.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("select u from UserEntity u where u.id =:id and u.isDeleted = false ")
    Optional<UserEntity> findUserById(UUID id);

    @Query("""
        select new com.techzen.techlearn.dto.response.UserResponseDTO(u.id, u.fullName, u.age)
        from UserEntity u where u.isDeleted = false
        """)
    Page<UserResponseDTO> findAllUser(Pageable pageable);
}
