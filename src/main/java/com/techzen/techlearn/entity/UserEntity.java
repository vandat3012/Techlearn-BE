package com.techzen.techlearn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
