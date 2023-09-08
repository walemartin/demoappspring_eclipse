package com.maburak.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.maburak.demo.model.Todo;
import com.maburak.demo.repository.TodoRepository;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Todo> getTodos() {
        return todoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Todo retrieveStudent(@PathVariable long id) throws Exception {
    	Optional<Todo> result = todoRepository.findById(id);

    	if (result.isEmpty())
    		throw new Exception("id-" + id);

    	return result.get();
    }
    
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo savedTodo= todoRepository.save(todo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(savedTodo.getId()).toUri();

    	return ResponseEntity.created(location).build();
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateStudent(@RequestBody Todo todo, @PathVariable long id) {

    	Optional<Todo> todoOps = todoRepository.findById(id);

    	if (todoOps.isEmpty())
    		return ResponseEntity.notFound().build();

    	todo.setId(id);
    	
    	todoRepository.save(todo);

    	return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
    	todoRepository.deleteById(id);
    }
    
}
