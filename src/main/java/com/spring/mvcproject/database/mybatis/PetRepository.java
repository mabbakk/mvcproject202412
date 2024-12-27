package com.spring.mvcproject.database.mybatis;

import com.spring.mvcproject.database.mybatis.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// CRUD 정의
@Mapper    // Bean 등록!
public interface PetRepository {
    // Create
    boolean save(Pet pet);

    // Read - Single Matching
    Pet findById(Long id);
    // Read - Multiple Matching
    List<Pet> findAll();

    // Update
    boolean updatePet(Pet pet);

    // Delete
    boolean deleteById(Long id);

    // Read - Count 유도
    int petCount();
}
