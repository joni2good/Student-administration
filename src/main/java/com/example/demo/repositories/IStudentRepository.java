package com.example.demo.repositories;

import com.example.demo.models.Student;

import java.util.List;

public interface IStudentRepository {
    // CRUD operations
    public boolean create(Student student);

    public Student read(int id);

    public List<Student> readAll();

    public List<Student> getAtt(int id);

    public boolean removeCourse(int stuId, int courseId);

    public List<Student> getAddStu(int courseId);

    public boolean addStu(int id, int course_id);

    public boolean update(Student student);

    public boolean delete(int id);
}


