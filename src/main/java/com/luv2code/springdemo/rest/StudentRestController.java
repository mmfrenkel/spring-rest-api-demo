package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	private List<Student> students;
	
	// @PostConstruct to load the student data only once when
	// bean is constructed
	@PostConstruct
	public void loadStudents() {
		
		students = new ArrayList<>();
		
		students.add(new Student("Emma", "Loeb"));
		students.add(new Student("Mario", "Atum"));
		students.add(new Student("Allie", "Troller"));
	}
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return students;
	}
	
	@GetMapping("students/{studentId}")
	public Student getStudent(@PathVariable int studentId) {
		
		// just index into the list for now to keep it simple
		if (studentId >= students.size() || studentId <0) {
			throw new StudentNotFoundException("studentId (" + studentId + ") not found.");
		}
		return students.get(studentId);
	}
}
