package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.author.dto.AuthorListDto;
import com.beyond.basic.b2_board.common.domain.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// JPA를 사용할 경우 Entity에 반드시 붙여야하는 어노테이션
// JPA의 EntityManager에게 객체를 위임하기 위한 어노테이션
// 엔티티매니저는 영속성 컨텍스트를 통해 DB 데이터 관리
@Entity
// Builder어노테이션을 통해 유연하게 객체 생성 가능
@Builder
public class Author extends BaseTimeEntity {
    @Id // pk설정
//    identity : auto_increment, auto : id 생성전략을 jpa에게 자동설정하도록 위임하는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 되도록이면 컬럼명과 변수명을 일치시키는 것이 개발의 혼선을 줄일 수 있음
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default  // 빌더패턴에서 변수 초기화(디폴트값)시 Builder.Default 어노테이션 필수
    private Role role = Role.USER;

//    OneToMany는 선택사항, 또한 default가 fetch = FetchType.LAZY
//    mappedBy에는 ManyToOne쪽에 변수명을 문자열로 지정, fk관리를 반대편(post)쪽에서 한다는 의미 -> 연관관계의 주인설정
//    cascade : 부모객체의 변화에 따라 자식객체가 같이 변하는 옵션 1)persist : 저장 2)remove : 삭제
//    orphanRemoval : 자식의 자식까지 모두 삭제할 경우 orphanRemoval=true 옵션 추가.
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY      )
    private Address address;


    public void updatePw(String password) {
        this.password = password;
    }

//    public AuthorDetailDto from() {
//        return new AuthorDetailDto(this.id, this.name, this.email);
//    }

    public AuthorListDto listFrom() {
        return new AuthorListDto(this.id, this.name, this.email);
    }
}
