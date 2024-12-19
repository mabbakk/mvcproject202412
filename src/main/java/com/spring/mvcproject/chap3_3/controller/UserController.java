package com.spring.mvcproject.chap3_3.controller;

import com.spring.mvcproject.chap3_3.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v3-3/users")  // 언더바 __는 잘 안 씀!! 웹브라우저에서 안 보임
public class UserController {

    private Map<Long, User> userStore = new HashMap<>();
    private long nextId = 1;

    // 사용자 생성
    @PostMapping
    public ResponseEntity<String> createUser0(@RequestBody User user) {
        // 앞으로 무조건 @Restcontroller의 리턴 타입은 ResponseEntity임!!

        // 검증
        if (user.getAge() < 0 ) {
            // headers 로 header를 여러 개 생성하여 보낼 수 있다.
            HttpHeaders headers = new HttpHeaders();
            headers.add("cause", "bad-age");
            headers.add("my-pet", "cat");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)  //숫자 입력했을 때 잘못 입력할 수 있으니 상수UNAUTHORIZED를 적어준다!
                    // 간혹 .___ 이렇게 뜨지 않는 것들이 있음! 그렇게 많이 유명하지 않은 것들.
                    // 그러면 그냥 위에 .status()로 적으면 됨!
                    .header("cause", "bad-age")  // 헤더를 조작할 수 있음 ResponseEntity 덕분에 사용할 수 있는 것.
                    .body("나이는 양수여야 합니다. age- " + user.getAge());
        }
        if (user.getName().isBlank()) {
            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
                    .badRequest()
                    .body("이름은 필수값입니다.");
        }

        user.setId(nextId++);
        System.out.println("user = " + user);

        userStore.put(user.getId(), user);
        return ResponseEntity
//                .status(HttpStatus.OK)
                .ok()  // 위에를 이처럼 써도 됨!
                .body("유저 정보가 생성되었습니다. - " + user);
    }
}
