package com.student.api.controller;

import com.student.api.domain.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        String sql= "select * from student";
  Map<String,String> paramMap= new HashMap();
    paramMap.put("","");


   List<Map<String,Object>> list=jdbcTemplate.queryForList(sql, paramMap);
   List<Student> l=new ArrayList<>();
   
for(Map<String,Object> m:list){
    long id=(long)m.get("id"); 
    String name=(String)m.get("name");
    Student stud=new Student();
    stud.setId(id);
    stud.setName(name);

    l.add(stud);


}
   if(l.size()>0){
       return ResponseEntity.ok().body(l);
   } 
     
     
        return ResponseEntity.ok().body(null);
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
         String sql="select * from student where id=?";
         Map<String,Long> paramMap=new HashMap<>();
         paramMap.put("id",id);
     Student   student=   jdbcTemplate.queryForObject(sql, paramMap, Student.class);
          if(student!=null){
       return ResponseEntity.ok().body(student);
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

        String sql="insert into student set(id,name) values(?,?)";
         Map<String,Object> paramSource=new HashMap<>();
         paramSource.put("id",student.getId());
         paramSource.put("name",student.getName());

      int res =   jdbcTemplate.update(sql, paramSource);
      if(res>0){
          return ResponseEntity.ok().build();
      }

        return ResponseEntity.badRequest().build();
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

        String sql= "update student set name=? where id=? ";
        Map<String,Object> paramSource=new HashMap<>();
        paramSource.put("id",student.getId());
        paramSource.put("name",student.getName());

 int result=jdbcTemplate.update(sql, paramSource);

if(result>0){
        return ResponseEntity.ok().build();
}

return ResponseEntity.badRequest().build();
    }

    /**
     * {@code DELETE  /student/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        String sql="delete student where id=?";
        Map<String,Long> paramMap=new HashMap<>();
        paramMap.put("id",id);
      int result=  jdbcTemplate.update(sql, paramMap);
       if(result>0){
        return ResponseEntity.ok().build();
       }

       return ResponseEntity.badRequest().build();
    }

}