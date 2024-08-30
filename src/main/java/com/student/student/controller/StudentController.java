package com.student.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.student.Entity.StudentEntity;
import com.student.student.repos.StudentRepo;


@RestController
public class StudentController {

    @Autowired
    StudentRepo repo;
    //get all the students
    //localhost:8080/students
    @GetMapping("/students")
    public List<StudentEntity> getAllStudents(){
        List<StudentEntity> students = repo.findAll();
        return students;
    }

    //localhost:8080/students/1
    @GetMapping("/students/{id}")
    public StudentEntity getStudent(@PathVariable int id) {
        StudentEntity student = repo.findById(id).get();

        return student;

    }

    @PostMapping("/student/add")
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity student) {
        StudentEntity newStudent = repo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);

    }

    @PutMapping("/student/update/{id}")
    public StudentEntity updateStudents(@PathVariable int id) {
        StudentEntity student = repo.findById(id).get();
        student.setName("poonam");
        student.setPercentage(92);
        repo.save(student);
        return student;

    }
    @DeleteMapping("/student/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeStudent(@PathVariable int id) {
        StudentEntity student = repo.findById(id).get();
        repo.delete(student);

    }

}
