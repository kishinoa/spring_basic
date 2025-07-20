package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dto.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dto.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dto.AuthorListDto;
import com.beyond.basic.b2_board.author.dto.AuthorUpdatePwDto;
//import com.beyond.basic.b2_board.repository.AuthorJdbcRepository;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// Component로도 대체 가능(트랜잭션 처리가 없는 경우)
@Service
@RequiredArgsConstructor
// 스프링에서 메서드 단위로 트랜잭션 처리를 하고, 만약 예외(unchecked)발생 시 자동 롤백처리 지원.
@Transactional
public class AuthorService {
//    의존성주입(DI)방법1. Autowired 어노테이션 사용 -> 필드주입
//    @Autowired
//    private AuthorRepositoryInterface authorRepository;

//    의존성주입(DI)방법2. 생성자주입방식(가장많이 쓰는 방식)
//    장점1) final을 통해 상수로 사용가능(안정성향상), 장점2) 다형성 구현가능, 장점3) 순환참조방지(컴파일타임에 check)
//    private final AuthorRepositoryInterface authorRepository;
//    객체로 만들어지는 시점에 스프링에서 authorRepository객체를 매개변수로 주입
//    @Autowired  // 생성자가 하나밖에 없을 때에는 Autowired 생략 가능
//    public AuthorService(AuthorMemoryRepository authorRepository) {
//        this.authorRepository = authorRepository;
//    }

//    의존성주입(DI)방법3. RequiredArgs 어노테이션 사용 -> 반드시 초기화 되어야 하는필드(final 등)을 대상으로 생성자를 자동생성
//    다형성 설계는 불가
    private final AuthorRepository authorRepository;

    private final PostRepository postRepository;

//    객체조립은 서비스 담당
    public void save(AuthorCreateDto authorCreateDto) {
//        이메일 중복 검증
        if(authorRepository.findByEmail(authorCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("존재하는 이메일입니다.");
        }
//        비밀번호 길이 검증
        if(authorCreateDto.getPassword().length() < 4) {
            throw new IllegalArgumentException("비밀번호 길이가 4자리 미만 입니다.");
        }
//        Author author = new Author(authorCreateDto.getName(), authorCreateDto.getEmail(), authorCreateDto.getPassword());
        Author author = authorCreateDto.authorToEntity();
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하는 값이 없습니다."));
//        return new AuthorDetailDto(author.getId(), author.getName(), author.getEmail());
//        변수명은 author지만 authorId로도 조회 가능
//        연관관계 없이 postRepository를 이용해 직접 count를 조회
//        List<Post> postList = postRepository.findByAuthorId(id);
//        List<Post> postList = postRepository.findByAuthor(author);
//        return AuthorDetailDto.fromEntity(author, postList.size());
//        Long postCount = postRepository.countByAuthorId(id);
//        return AuthorDetailDto.fromEntity(author, postCount);
//        OneToMany 연관관계를 이용해 조회
        return AuthorDetailDto.fromEntity(author);
    }

//    트랜잭션이 필요없는 경우, 아래와 같이 명시적으로 제외
    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll() {
//        List<Author> authors = authorRepository.findAll();
//        List<AuthorListDto> authorListDtos = new ArrayList<>();
//        for(Author a : authors) {
//            authorListDtos.add(a.listFrom());
//        }
        return authorRepository.findAll().stream().map(a->a.listFrom()).collect(Collectors.toList());
    }

    public void updatePassword(AuthorUpdatePwDto authorUpdatePwDto) {
        Author author = authorRepository.findByEmail(authorUpdatePwDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));
        if(authorUpdatePwDto.getPassword().length() < 4) {
            throw new IllegalArgumentException("비밀번호 길이가 4자리 미만 입니다.");
        }
//        dirty checking : 객체를 수정한 후 별도의 update쿼리 발생시키지 않아도, 영속성 컨텍스트에 의해 객체 변경사항 자동 DB반영
        author.updatePw(authorUpdatePwDto.getPassword());
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 계정이 존재하지 않습니다."));
        authorRepository.delete(author);
    }
}
