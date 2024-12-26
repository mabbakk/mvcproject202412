package com.spring.mvcproject.database.jdbc.repository;


import com.spring.mvcproject.database.jdbc.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 데이터베이스에 접근하는 객체
@Repository
public class PersonRepository {

    // Database에 로그인할 정보
    private String username = "root";
    private String password = "mariadb";
    // 데이터베이스가 설치된 주소 (JDBC URL)
    private String url = "jdbc:mariadb://localhost:3306/practice";


    // 전용 드라이버 클래스
    private String driberCalssName = "org.mariadb.jdbc.Driver";    // 데이터베이스 전용 드라이버 로딩
    public PersonRepository() {
        try {
            // 전용 로딩 클래스 - DB마다 다름
            Class.forName(driberCalssName);
        } catch (ClassNotFoundException e) {
            System.out.println("DB 연결 실패!");
        }
    }

    // INSERT
    public void save(Person person) {
        String sql = """
                    INSERT INTO tbl_person
                        (id, person_name, age)
                    VALUES
                        (?, ?, ?)
                """;


        // 1. DB에 접속하고 접속 정보를 받아옴
        // try ~ with resources
        try (Connection conn = DriverManager.getConnection(url, username, password)){ // DB 접속 정보를 가지고 있는 객체 conn
        
            // 2. SQL을 실행할 수 있는 실행기 객체를 가져옴
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // 3. ? 값을 세팅
            pstmt.setLong(1, person.getId());
            pstmt.setString(2, person.getPersonName());  // 브라우저에서 어떤 값을 보낼지 모르니 get__()으로 변수를 설정해두는 것!
            pstmt.setInt(3, person.getAge());
            
            // 4. SQL 실행 명령
            //   4-a : 갱신 (INSERT, UPDATE, DELETE) 명령   : excuteUpdate()
            //   4-b : 조회 (SELECT) 명령                   : excuteQuery()
            pstmt.executeUpdate();   // 맨 위에서 값을 INSERT 하고 있으니 executeUpdate()!
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(Person person) {
        String sql = """
                    UPDATE tbl_person
                    SET person_name=?, age=?
                    WHERE id=?
                """;

        try {
            // 1. DB에 접속하고 접속 정보를 받아옴
            Connection conn = DriverManager.getConnection(url, username, password);  // DB 접속 정보를 가지고 있는 객체 conn

            // 2. SQL을 실행할 수 있는 실행기 객체를 가져옴
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 3. ? 값을 세팅
            pstmt.setString(1, person.getPersonName());  // 브라우저에서 어떤 값을 보낼지 모르니 get__()으로 변수를 설정해두는 것!
            pstmt.setInt(2, person.getAge());
            pstmt.setLong(3, person.getId());

            // 4. SQL 실행 명령
            //   4-a : 갱신 (INSERT, UPDATE, DELETE) 명령   : excuteUpdate()
            //   4-b : 조회 (SELECT) 명령                   : excuteQuery()
            pstmt.executeUpdate();   // 맨 위에서 값을 INSERT 하고 있으니 executeUpdate()!
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(Long id) {
        String sql = """
                    DELETE FROM tbl_person
                    WHERE id = ?
                """;

        try {
            // 1. DB에 접속하고 접속 정보를 받아옴
            Connection conn = DriverManager.getConnection(url, username, password);

            // 2. SQL을 실행할 수 있는 실행기 객체를 가져옴
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 3. ?값을 세팅
            pstmt.setLong(1, id);

            // 4. SQL 실행 명령
            //   4-a : 갱신(INSERT, UPDATE, DELETE) 명령 : executeUpdate()
            //   4-b : 조회(SELECT) 명령                 : executeQuery()
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 다중 SELECT -  목록 조회
    public List<Person> findAll() {
        String sql = """
                        SELECT * FROM tbl_person
                """;

        List<Person> personList = new ArrayList<>();

        try {
            // 1. DB에 접속하고 접속 정보를 받아옴
            Connection conn = DriverManager.getConnection(url, username, password);

            // 2. SQL을 실행할 수 있는 실행기 객체를 가져옴
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 3. ?값을 세팅


            // 4. SQL 실행 명령
            //   4-a : 갱신(INSERT, UPDATE, DELETE) 명령 : executeUpdate()
            //   4-b : 조회(SELECT) 명령                 : executeQuery()

//            pstmt.executeUpdate();  // 영향 받는 행(row)의 개수를 나타내는 리턴 타입 int

            // ResultSet : 조회 결과로 나오는 2차원의 표
            //  -> 표에 접근하여 데이터를 조작할 수 있다.
            ResultSet rs = pstmt.executeQuery();


            // next() : 포인터를 한 행씩 이동
            while (rs.next()) {
                // 커서가 가리키는 행의 데이터 추출
                long id = rs.getLong("id");
                String personName = rs.getString("person_name");
                int age = rs.getInt("age");


                // ORM
                Person p = new Person(id, personName, age);
                System.out.println("p = " + p);

                personList.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return personList;
    }


}
