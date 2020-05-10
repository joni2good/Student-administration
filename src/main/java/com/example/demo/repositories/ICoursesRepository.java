package com.example.demo.repositories;

import com.example.demo.models.Course;
import java.util.List;

public interface ICoursesRepository {
    // CRUD operations
    public boolean create(Course course);

    public Course read(int id);

    public List<Course> readAll();

    public boolean update(Course course);

    public boolean delete(int id);
}
