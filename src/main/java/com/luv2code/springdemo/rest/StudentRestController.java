package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
        
        StudentErrorResponse error = new StudentErrorResponse();
        
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        
        // error = body, status code = HttpStatus.
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    /* Catch all for any unknown exception/issues */
    @ExceptionHandler  
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
        
        StudentErrorResponse error = new StudentErrorResponse();
        
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        
        // error = body, status code = HttpStatus.
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
