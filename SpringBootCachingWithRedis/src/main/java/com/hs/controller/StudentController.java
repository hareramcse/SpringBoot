package com.hs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.exception.StudentNotFoundException;
import com.hs.model.Student;
import com.hs.service.StudentService;


@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") Integer id) throws StudentNotFoundException {
		System.out.println("Entered into GetStudent method");
		Student student = studentService.getStudentById(id);
		return ResponseEntity.status(HttpStatus.OK).body(student);
	}
	
	@PostMapping
	public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student)  {
		Student savedStudent = studentService.saveStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
	}
	
	@PutMapping
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) throws StudentNotFoundException{
	    Student updatedStudent = studentService.updateStudent(student);
	    return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id) throws StudentNotFoundException{
	     studentService.deleteStudent(id);
	     return ResponseEntity.noContent().build();
	}

}
