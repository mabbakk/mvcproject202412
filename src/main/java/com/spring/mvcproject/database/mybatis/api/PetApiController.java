package com.spring.mvcproject.database.mybatis.api;

import com.spring.mvcproject.database.mybatis.PetRepository;
import com.spring.mvcproject.database.mybatis.dto.response.PetListResponse;
import com.spring.mvcproject.database.mybatis.dto.response.PetResponse;
import com.spring.mvcproject.database.mybatis.entity.Pet;
import com.spring.mvcproject.database.mybatis.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetApiController {

    private final PetService petService;

    // 목록 조회
    @GetMapping
    public ResponseEntity<?> list() {

        PetListResponse list = petService.getList();

        return ResponseEntity.ok().body(list);
    }

    // 개별 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Pet pet = petService.getPet(id);

        return ResponseEntity.ok().body(pet);
    }

    // 생성
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Pet pet) {
        boolean flag = petService.createPet(pet);

        return ResponseEntity
                .ok()
                .body("애완동물 생성 성공! ");
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> modify(
            @PathVariable Long id
            , @RequestBody Pet pet
    ) {
        pet.setId(id);
        petService.updatePet(pet);
        return ResponseEntity.ok()
                .body("수정 성공!");
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.ok().body("삭제성공!");
    }
}