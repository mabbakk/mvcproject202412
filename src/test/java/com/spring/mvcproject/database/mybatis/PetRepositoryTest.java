package com.spring.mvcproject.database.mybatis;

import com.spring.mvcproject.database.mybatis.entity.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Test
    void saveTest() {
        Pet pet = Pet.builder()
                .petName("꾸릉꾸릉")
                .petAge(15)
                .injection(true)
                .build();

        petRepository.save(pet);
    }

    @Test
    void findIdTest() {
        Pet foundPet = petRepository.findById(2L);

        System.out.println("\n\nfoundPet = " + foundPet);
    }

    @Test
    void findAllTest() {
        List<Pet> petList = petRepository.findAll();

        System.out.println("=========List of pet========");
        petList.forEach(System.out::println);
    }

    @Test
    void deleteByIdTest() {
        petRepository.deleteById(2L);

        Pet pet = petRepository.findById(2L);
        System.out.println("pet = " + pet);
    }

    @Test
    void countTest() {
        int count = petRepository.petCount();
        System.out.println("count = " + count);
    }

}