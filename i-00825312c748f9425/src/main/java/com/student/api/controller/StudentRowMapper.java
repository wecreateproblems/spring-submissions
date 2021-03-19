package com.student.api.controller;

import com.student.api.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRowMapper extends JpaRepository<Student,Long>{
   
}