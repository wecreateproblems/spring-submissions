package com.student.api.controller;

import com.student.api.domain.Student;
import com.student.api.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing student system process. Use
 * {@link StudentRowMapper} to map database rows to Student entity object.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    // use JdbcTemplate to query for students aganist database
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@code GET  /students} : get all the Students.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> allStudnet = studentRepository.findAll();
        return ResponseEntity.ok().body(allStudnet);
    }

    /**
     * {@code GET  /students/:id} : get the "id" Student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the student, or if does not exist, return with status "noContent".
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {

        Optional<Student> studnet = studentRepository.findById(id);
        return ResponseEntity.ok().body(studnet.get());
    }

    /**
     * {@code POST  /student} : Create a new student.
     *
     * @param student the student to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the new student
     */
    @PostMapping("/students")
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        
         studentRepository.save(student);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /student} : Updates an existing student.
     *
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the updated student.
     */
    @PutMapping("/students")
    public ResponseEntity<Void> updateStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /student/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}