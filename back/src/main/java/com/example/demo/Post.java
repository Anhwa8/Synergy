package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) //Id 수정 불가
    private Long id;
    private String title;
    @Column(updatable = false) //Created 수정 불가
    private String created;
    private String updated;
    @Column(updatable = false) // Name 수정 불가
    private String name;
    private String manual;
    private String content;
}
