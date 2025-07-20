//package com.beyond.basic.b2_board.repository;
//
//import com.beyond.basic.b2_board.domain.Author;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class AuthorJdbcRepository {
//
////    DataSource는 DB와 JDBC에서 사용하는 DB 연결 드라이버 객체
////    application.yml에 설정한 DB 정보를 사용하여 dataSource 객체 싱글톤 생성
//    @Autowired
//    private DataSource dataSource;
//
////    jdbc의 단점
////    1. raw쿼리에서 오타가 나도 디버깅 어려움
////    2. 데이터 추가 시 매개변수와 컬럼의 매핑을 수작업
////    3. 데이터 조회시, 객체 조립을 직접
//    public void save(Author author) {
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "insert into author(name, email, password) values(?, ?, ?);";
////            PreparedStatement 객체로 만들어서 실행가능한 상태로 만드는 것.
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, author.getName());
//            ps.setString(2, author.getEmail());
//            ps.setString(3, author.getPassword());
//
//            ps.executeUpdate(); // 추가, 수정의 경우는 executeUpdate, 조회는 executeQuery
//        } catch (SQLException e) {
////            unchecked 예외는 spring에서 트랜잭션 상황에서 롤백의 기준
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Author> findAll() {
//        List<Author> authorList = new ArrayList<>();
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "select * from author;";
//            PreparedStatement ps = connection.prepareStatement(sql);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                Long id = rs.getLong(1);
//                String name = rs.getString(2);
//                String getEmail = rs.getString(3);
//                String password = rs.getString(4);
//                Author author = new Author(id, name, getEmail, password);
//                authorList.add(author);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return authorList;
//    }
//
//    public Optional<Author> findById(Long id) {
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "select * from author where id = ?;";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setLong(1, id);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                Long getId = rs.getLong(1);
//                String getName = rs.getString(2);
//                String getEmail = rs.getString(3);
//                String getPassword = rs.getString(4);
//                Author author = new Author(getId, getName, getEmail, getPassword);
//                return Optional.of(author);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }
//
//    public Optional<Author> findByEmail(String email) {
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "select * from author where email = ?;";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, email);
//
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                Long getId = rs.getLong(1);
//                String getName = rs.getString(2);
//                String getEmail = rs.getString(3);
//                String getPassword = rs.getString(4);
//                Author author = new Author(getId, getName, getEmail, getPassword);
//                return Optional.of(author);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }
//
//    public void delete(Author author) {
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql = "delete from author where id = ?";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setLong(1, author.getId());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
