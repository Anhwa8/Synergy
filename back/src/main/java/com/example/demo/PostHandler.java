//package com.example.demo;
//
//import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
//import org.springframework.data.rest.core.annotation.HandleBeforeSave;
//import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Component
//@RepositoryEventHandler
//public class PostHandler {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//    private String firstName;
//
//    @HandleBeforeCreate // POST
//    public void handleBeforeCreate(Post entity) {
//        LocalDateTime now = LocalDateTime.now();
//        String createdDate = now.format(formatter);
//        firstName = entity.getName();
//        entity.setCreated(createdDate);
//        entity.setTitle("[" + entity.getName() + "] " + entity.getTitle());
//    }
//
//    @Transactional
//    @HandleBeforeSave // PUT, PATCH
//    public void handleBeforeSave(Post entity) {
//        if (firstName != null && firstName.equals(entity.getName())) {
//            LocalDateTime now = LocalDateTime.now();
//            String savedDate = now.format(formatter);
//            entity.setUpdated(savedDate);
//            entity.setTitle("[" + firstName + "] " + entity.getTitle());
//        }
//    }
//}

package com.example.demo;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RepositoryEventHandler
public class PostHandler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private String firstName;
    // 저장 하기 전 DB에 넣을 때,
    @HandleBeforeCreate
    public void handleBeforeCreate(Post entity) {
        LocalDateTime now = LocalDateTime.now();
        String createdDate = now.format(formatter);
        firstName = entity.getName();
        entity.setCreated(createdDate);
        entity.setTitle("[" + entity.getName() + "] " + entity.getTitle());
    }

    @HandleBeforeSave // PUT, PATCH
    public void handleBeforeSave(Post entity) {
        if (firstName != null && firstName.equals(entity.getName())) {
            LocalDateTime now = LocalDateTime.now();
            String savedDate = now.format(formatter);
            entity.setUpdated(savedDate);
            entity.setTitle("[" + firstName + "] " + entity.getTitle());
        }
    }

    /*
    PUT: 일괄적으로 데이터를 수정, Body에 값이 없을 경우 null로 저장
    PATCH: Body에 존재하는 값만 업데이트
     */
}
