package com.student.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<StudentEntity> updateStudents(@PathVariable int id, @RequestBody StudentEntity updatedStudent) {
        Optional<StudentEntity> optionalStudent = repo.findById(id);

        if (optionalStudent.isPresent()) {
            StudentEntity student = optionalStudent.get();

            // Update the student's fields with the data from the request body
            if (updatedStudent.getName() != null) {
                student.setName(updatedStudent.getName());
            }
            if (updatedStudent.getPercentage() != null) {
                student.setPercentage(updatedStudent.getPercentage());
            }
            if(updatedStudent.getBranch() != null){
                student.setBranch(updatedStudent.getBranch());
            }

            repo.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if the student is not found
        }
    }

//    @DeleteMapping("/student/delete/{id}")
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void removeStudent(@PathVariable int id) {
//        StudentEntity student = repo.findById(id).get();
//        repo.delete(student);
//
//    }

    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<String> removeStudent(@PathVariable int id) {
        Optional<StudentEntity> student = repo.findById(id);

        if (student.isPresent()) {
            repo.delete(student.get());
            return ResponseEntity.ok("Student with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID " + id + " not found.");
        }
    }


}
