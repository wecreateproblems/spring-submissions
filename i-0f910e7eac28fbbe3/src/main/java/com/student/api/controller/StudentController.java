package com.student.api.controller;

import com.student.api.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing student system process. Use
 * {@link StudentRowMapper} to map database rows to Student entity object.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    // use JdbcTemplate to query for students aganist database
    @Autowired
    StudentRowMapper studentRowMapper;
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
        String sql = "select * from student";
        return ResponseEntity.ok().body(jdbcTemplate.query(sql, studentRowMapper));
    }

    /**
     * {@code GET  /students/:id} : get the "id" Student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the student, or if does not exist, return with status "noContent".
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) throws EmptyResultDataAccessException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);

        String sql = "select * from student where id=?";
        return ResponseEntity.ok().body(jdbcTemplate.queryForObject(sql, map, studentRowMapper));
    }

    /**
     * {@code POST  /student} : Create a new student.
     *
     * @param student the student to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the new student
     */
    @PostMapping("/students")
    public ResponseEntity<Void> createStudent(@RequestBody Student student) throws EmptyResultDataAccessException{

        String sql = "delete from student where id=?";
        HashMap<String, Object> map = new HashMap<>();

        map.put("name", student.getName());
        jdbcTemplate.update(sql, map);
        
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
    public ResponseEntity<Void> updateStudent(@RequestBody Student student) throws EmptyResultDataAccessException {
        String sql = "delete from student where id=?";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", student.getId());
        map.put("name", student.getName());
        jdbcTemplate.update(sql, map);
        return ResponseEntity.ok().build();

    }

    /**
     * {@code DELETE  /student/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws EmptyResultDataAccessException {
        String sql = "delete from student where id=?";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        jdbcTemplate.update(sql, map);
        return ResponseEntity.ok().build();
    }

}