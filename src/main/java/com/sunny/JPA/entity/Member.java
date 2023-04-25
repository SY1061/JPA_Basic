package com.sunny.JPA.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "MEMBERS")
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;
    @Column(name = "MEMBER_NAME")
    private String memberName;
}
