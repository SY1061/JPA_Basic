package com.sunny.JPA.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter
public class Member {
    @Id
    private Long id;
    @Column
    private String name;
}
