package com.beyond.basic.b2_board.author.dto;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data  // dto 계층은 데이터의 안정성이 엔티티만큼 중요하지 않으므로, setter도 일반적으로 추가
public class AuthorCreateDto {
    @NotEmpty(message = "이름은 필수 입력 항목입니다.")
    private String name;
    @NotEmpty(message = "email은 필수 입력 항목입니다.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 4, message = "비밀번호는 4자리 이상이어야 합니다.")
    private String password;

    public Author authorToEntity(String encodedPassword) {
//        빌더패턴은 매개변수의 개수와 매개변수의 순서에 상관없이 객체생성가능
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}
