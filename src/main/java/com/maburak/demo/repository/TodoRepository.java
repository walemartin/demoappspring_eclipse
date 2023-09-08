package com.maburak.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maburak.demo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
