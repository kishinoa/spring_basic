package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.dto.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dto.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.author.dto.CommonDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Controller + ResponseBody
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
//    회원 가입
    @PostMapping("/create")
//    dto에 있는 validation어노테이션과 controller @Valid 한쌍
    public ResponseEntity<?> save(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
//        try {
//            this.authorService.save(authorCreateDto);
//            return new ResponseEntity<>("OK", HttpStatus.CREATED);
//        }
//        catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        ControllerAdvice가 없었으면 위와 같이 개벽적인 예외처리가 필요하나, 이제는 전역적인 예외처리가 가능
        this.authorService.save(authorCreateDto);
        return new ResponseEntity<>(new CommonDto("OK", HttpStatus.CREATED.value(), "created"), HttpStatus.CREATED);

    }

//    회원 목록 조회 /author/list
    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }


//    회원 상세 조회 : id로 조회 /author/detail/1
//    서버에서 별도의 try catch하지 않으면, 에러 발생시 500에러 + 스프링의 포맷으로 에러 리턴
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new CommonDto(authorService.findById(id), HttpStatus.OK.value(), "found author") , HttpStatus.OK);
    }

//    비밀번호 수정 : /author/updatepw  email, password -> json
//    get : 조회, post : 등록, patch : 부분수정, put : 대체, delete : 삭제
    @PatchMapping("/updatepw")
    public ResponseEntity<?>  updatePw(@RequestBody AuthorUpdatePwDto authorUpdatePwDto) {
        authorService.updatePassword(authorUpdatePwDto);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

//    회원 탈퇴(삭제) : /author/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
