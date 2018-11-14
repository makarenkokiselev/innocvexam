package com.pablo.innocvexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.innocvexam.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}