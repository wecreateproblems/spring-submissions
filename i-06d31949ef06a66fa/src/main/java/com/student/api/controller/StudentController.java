package com.student.api.controller;

import com.student.api.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.*;

/**
 * REST controller for managing student system process. Use {@link StudentRowMapper} to map database rows to Student entity object.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    // use JdbcTemplate to query for students aganist database
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    StudentRowMapper studentRowMapper;

    public StudentController(NamedParameterJdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = studentRowMapper;
    }

    /**
     * {@code GET  /students} : get all the Students.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = new ArrayList<Student>();
        MapSqlParameterSource paramResource = new MapSqlParameterSource();
        
        try{
            studentList = jdbcTemplate.query("select * from student", studentRowMapper);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(studentList);
    }

    /**
     * {@code GET  /students/:id} : get the "id" Student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the student, or if does not exist, return with status "noContent".
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = new Student();
        try{
            MapSqlParameterSource paramResource = new MapSqlParameterSource();
            paramResource.addValue("id", id);
            student = jdbcTemplate.queryForObject("select * from student where id = ?", paramResource, studentRowMapper);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(student);
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
        try{
            MapSqlParameterSource paramResource = new MapSqlParameterSource();
            String name = student.getName();
            paramResource.addValue("name", name);
            jdbcTemplate.update("insert into student values(?)", paramResource);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
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
        try{
            MapSqlParameterSource paramResource = new MapSqlParameterSource();
            String name = student.getName();
            Long id = student.getId();
            paramResource.addValue("name", name);
            paramResource.addValue("id", id);
            jdbcTemplate.update("update student set name = ? where id = ?", paramResource);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
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
        try{
            MapSqlParameterSource paramResource = new MapSqlParameterSource();
            paramResource.addValue("id", id);
            jdbcTemplate.update("delete from student where id = ?", paramResource);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}