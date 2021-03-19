package com.student.api.controller;

import com.student.api.domain.Student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StudentRowMapper implements RowMapper<Student> {
    private SessionFactory sessionFactory;
     /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /**
     *
     * @param rs     Database ResultSet object. Get database data with the column
     *               names "ID" and "NAME". Remember, "ID" column is Long data type
     *               and "NAME" column is String data type.
     * @param rowNum If you get data with column names as described above, you don't
     *               need to use rowNum parameter
     * @return Student object with the mapped values from database
     */
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setName(rs.getString("name"));
        return student;
    }

    public List<Student> getAllStudents(){
       Session session = this.sessionFactory.getCurrentSession();
       List<Student> studentList = session.createQuery("from Student").list();
      return studentList;

    }

    public Student getStudentByID(Long id){
        Session session = this.sessionFactory.getCurrentSession();
        Student s = (Student)session.load(Student.class, new Long(id));
        return s;
    }

    public void addStudent(Student s){
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(s);
    }

    public void updateStudent(Student s){
        Session session = this.sessionFactory.getCurrentSession();
        session.update(s);
    }

    public void deleteStudent(Long id){
        Session session = this.sessionFactory.getCurrentSession();
        Student s = (Student)session.load(Student.class, new Long(id));
        if(null != s){
            session.delete(s);
        }
       
    }
}