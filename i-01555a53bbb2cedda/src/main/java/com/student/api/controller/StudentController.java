package com.student.api.controller;

import com.student.api.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
// import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Map;
import  com.student.api.Respository.StudentRepository;

/**
 * REST controller for managing student system process. Use {@link StudentRowMapper} to map database rows to Student entity object.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {
       
    @Autowired
    StudentRepository studentRepo;
    // use JdbcTemplate to query for students aganist database
    // private final NamedParameterJdbcTemplate jdbcTemplate;

    // public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
    //     this.jdbcTemplate = jdbcTemplate;
    // }


    /**
     * {@code GET  /students} : get all the Students.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        final List<Student> allStudentDetails = studentRepo.findAll();
        return ResponseEntity.ok().body(allStudentDetails);
    }

    /**
     * {@code GET  /students/:id} : get the "id" Student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the student, or if does not exist, return with status "noContent".
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable final Long id) {
        final Student studentDetails = studentRepo.findById(id).get();
        return ResponseEntity.ok().body(studentDetails);
    }

    /**
     * {@code POST  /student} : Create a new student.
     *
     * @param student the student to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the new student
     */
    @PostMapping("/students")
    public ResponseEntity<Void> createStudent(@RequestBody final Student student) {
        studentRepo.save(student);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /student} : Updates an existing student.
     *
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated student.
     */
    @PutMapping("/students")
    public ResponseEntity<Void> updateStudent(@RequestBody final Student student) {
        studentRepo.save(student);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /student/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable final Long id) {
        
        studentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}