package com.spring.mvcproject.chap2_4.controller;


import com.spring.mvcproject.chap2_4.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    // 데이터베이스 대용으로 책들을 모아서 관리
    private Map<Long, Book> bookStore = new HashMap<>();
    // List<>를 쓰면 무조건 순서에 맞게 인덱스까지 알아내야 한다.
    // Map은 인덱스의 순서가 뒤죽박죽이어도 찾아낼 수 있다.

    // 도서 아이디 자동 생성 (안 겹치는 게 중요함! ex. 회원 정보 관리할 때 안 겹치는 이메일 같은 거 사용하면 좋음.)
    private long nextid = 1;

    // 생성자
    public BookController() {
//        bookStore.put(1L, new Book());  이게 원래 기본!
        bookStore.put(nextid, new Book(nextid, "잭과 콩나물", "뽀로로", 2500));
        nextid++;
        bookStore.put(nextid, new Book(nextid, "헨젤과 그레텔", "루피", 3000));
        nextid++;
        bookStore.put(nextid, new Book(nextid, "콩쥐팥쥐", "크롱", 4500));
        nextid++;
    }

    // 1. 전체 도서 조회 요청 처리

    @GetMapping
    public List<Book> List() {
        // 아래 주석과 같이 작성할 수도 있지만
//        return bookStore.values().stream().collect(Collectors.toList());
        // 이 코드와 같이 bookstore의 values 즉, newBook() 의 정보들만 꺼내올 수 있다!
        return new ArrayList<>(bookStore.values());
    }


    // 2. 개별 조회
    // 조회는 GET-!
    @GetMapping("{id}")

    public Object getBook(
            @PathVariable long id)
    {

//        System.out.println("/books/%s  : GET 요청이 들어옴!".formatted(id));
//        System.out.println("id = " + id);

        Book book = bookStore.get(id);

        // 도서 목록에 해당 도서가 없을 때
        if (book == null) {
            return "해당 도서는 존재하지 않습니다. :id - " + id;
        }

        return book;
    }


    // 3. 도서 생성 요청
    @PostMapping
    public String addBook(  // @RequestParam 생략 가능!!!
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam int price
    ) {

        // 새 도서 객체 생성 후 맵에 저장
        Book newBook = new Book(nextid++, title, author, price);
        bookStore.put(newBook.getId(), newBook);

        return "도서가 추가되었습니다! - " + newBook.getId();
    }


    // 4. 도서 수정
    @PutMapping("/{id}")
    public String updateBook(
            @PathVariable Long id,
            String title, String author, int price
    ) {
        // 기존 도서 객체를 조회
        Book foundBook = bookStore.get(id);

        if (foundBook == null) {
            return id + "번 도서는 존재하지 않습니다. 수정 실패!";
        }

        // 새 데이터로 수정
        foundBook.updateBook(title, author, price);

        return "도서 수정 완료: " + foundBook.getId();
    }

    // 5. 도서 삭제
    @DeleteMapping("/{id}")
    public String deleteBook(
            @PathVariable Long id
    ) {
        // 맵에서 데이터를 제거
        Book removed = bookStore.remove(id);

        if (removed == null) {
            return id + "번 도서는 존재하지 않습니다. 삭제 실패!";
        }

        return "도서 삭제 완료: " + id;
    }

    // 6. 현재 도서가 몇권 있는지 알려주세요 요청
    @GetMapping("/count")
    public String count() {
        return "현재 저장된 도서의 개수: " + bookStore.size() + "권";
    }

}
