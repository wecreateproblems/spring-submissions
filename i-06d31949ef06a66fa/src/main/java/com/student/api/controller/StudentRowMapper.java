package com.student.api.controller;

import com.student.api.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student> {
    /**
     *
     * @param rs Database ResultSet object. Get database data with the column names "ID" and "NAME". Remember, "ID" column is Long data type and "NAME" column is String data type.
     * @param rowNum If you get data with column names as described above, you don't need to use rowNum parameter
     * @return Student object with the mapped values from database
     */
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setName(rs.getString("name"));
        return student;
    }
}