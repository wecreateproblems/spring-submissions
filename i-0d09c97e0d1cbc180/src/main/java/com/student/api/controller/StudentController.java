package com.student.api.controller;

import com.student.api.domain.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing student system process. Use {@link StudentRowMapper} to map database rows to Student entity object.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    // use JdbcTemplate to query for students aganist database
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@code GET  /students} : get all the Students.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        StudentRowMapper rowmapper=new StudentRowMapper();
        String sql="Select * from student";
        List<Student> stud=  jdbcTemplate.query(sql,rowmapper);
        return ResponseEntity.ok().body(stud);
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
        StudentRowMapper rowmapper=new StudentRowMapper();
        Map<String,Long> map =new HashMap<>();
        
        String sql="Select * from student where id="+id;
        map.put("id", id);
        Student stud=  (Student) jdbcTemplate.queryForStream(sql, map, rowmapper);
        if(stud!=null)
        {
            return ResponseEntity.ok().body(stud);
        }
       
            return ResponseEntity.ok().body(null);
        
       
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

        PreparedStatementCallback pf;
        PreparedStatement ps;
        SqlParameterSource sq;
       // sq.
        Map<String,Student> map=new HashMap<>();
        String sql="Insert INTO student values (? ,?,?,?)";
        map.put("student", student);
       // jdbcTemplate.execute(sql, student, ps.execute());
     //  Student stud=  jdbcTemplate.execute(sql,ps.executeQuery());
       // (sql, map, ps.execute());
    }
    catch( Exception e)
    {
        return ResponseEntity.noContent().build();
       // e.pr
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
            PreparedStatement ps;
            SqlParameterSource sq;
           // sq.
            Map<String,Student> map=new HashMap<>();
            String sql="Insert INTO student values ";
            map.put("student", student);
           // jdbcTemplate.execute(sql, student, ps.execute());
          //  jdbcTemplate.execute(sql, map, ps.execute());
           // (sql, map, ps.execute());
        }
        catch( Exception e)
        {
            return ResponseEntity.noContent().build();
           // e.pr
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
            StudentRowMapper rowmapper=new StudentRowMapper();
           // Map<String,Student> map=new HashMap<>();
            String sql="Delete from  student Where id= "+id;
              jdbcTemplate.query(sql,rowmapper);
        }
        catch( Exception e)
        {
            return ResponseEntity.noContent().build();
           // e.pr
        }
        
        return ResponseEntity.ok().build();
    }

}