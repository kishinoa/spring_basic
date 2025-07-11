package com.beyond.basic.b1_hello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Getter // 클래스내의 모든 변수를 대상으로 getter가 생성
@Data // getter, setter, tostring 메서드까지 모두 만들어주는 어노테이션
@AllArgsConstructor // 모든 매개변수가 있는 생성자
@NoArgsConstructor // 기본생성자
// 기본생성자 + getter로 parsing이 이뤄지므로 보통은 필수적 요소
public class Hello {
    private String name;
    private String email;
}
