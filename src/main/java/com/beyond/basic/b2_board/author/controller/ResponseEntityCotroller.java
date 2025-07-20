package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dto.CommonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityCotroller {

//    case1. @ResponseStatus 어노테이션 사용
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/annotation1")
    public String annotation1() {
        return "OK";
    }

//    case2. 메서드 체이닝 방식
    @GetMapping("/chainning1")
    public ResponseEntity<?> chainning1() {
        Author author = Author.builder().name("test").email("test@naver.com").password("1234").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

//    case3. ResponseEntity 객체를 직접 생성하는 방식(가장많이사용)
    @GetMapping("/custom1")
    public ResponseEntity<?> custom1() {
        Author author = Author.builder().name("test").email("test@naver.com").password("1234").build();
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/custom2")
    public ResponseEntity<?> custom2() {
        Author author = Author.builder().name("test").email("test@naver.com").password("1234").build();
        return new ResponseEntity<>(new CommonDto(author, HttpStatus.CREATED.value(), "author is created"), HttpStatus.CREATED);
    }
}
