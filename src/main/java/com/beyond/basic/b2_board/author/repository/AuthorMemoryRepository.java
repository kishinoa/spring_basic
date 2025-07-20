package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorMemoryRepository {
    private List<Author> authorList = new ArrayList<>();
    public static Long id = 1L;

    public void save(Author author) {
        this.authorList.add(author);
        id++;
    }

    public List<Author> findAll() {
        return this.authorList;
    }

    public Optional<Author> findById(Long id) {
        for(Author a : authorList) {
            if(a.getId().equals(id)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public Optional<Author> findByEmail(String email) {
        for(Author a : authorList) {
            if(a.getEmail().equals(email)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    public void delete(Author author) {
//        id 값으로 요소의 index 값을 찾아 삭제
        authorList.remove(author);
    }
}
