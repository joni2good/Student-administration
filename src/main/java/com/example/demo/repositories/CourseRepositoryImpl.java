package com.example.demo.repositories;

import com.example.demo.models.Course;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryImpl implements ICoursesRepository {
    private Connection conn;

    public CourseRepositoryImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public boolean create(Course course){
        try {
            PreparedStatement createCourse = conn.prepareStatement("INSERT INTO courses (cName, loc, ects, startDate) VALUES (?, ?, ?, ?)");
            createCourse.setString(1, course.getCName());
            createCourse.setString(2, course.getLoc());
            createCourse.setInt(3, course.getEcts());
            createCourse.setDate(4, new java.sql.Date(course.getStartDate().getTime()));
            createCourse.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Course read(int id){
        Course courseToReturn = new Course();
        try{
            PreparedStatement getSingleCourse = conn.prepareStatement("SELECT * FROM courses WHERE course_id=?");
            getSingleCourse.setInt(1, id);
            ResultSet rs = getSingleCourse.executeQuery();
            while(rs.next()){
                courseToReturn = new Course();
                courseToReturn.setId(rs.getInt(1));
                courseToReturn.setCName(rs.getString(2));
                courseToReturn.setLoc(rs.getString(3));
                courseToReturn.setEcts(rs.getInt(4));
                courseToReturn.setStartDate(rs.getDate(5));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return courseToReturn;
    }

    @Override
    public List<Course> readAll(){
        List<Course> allCourses = new ArrayList<Course>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM courses");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Course tempCourse = new Course();
                tempCourse.setId(rs.getInt(1));
                tempCourse.setCName(rs.getString(2));
                tempCourse.setLoc(rs.getString(3));
                tempCourse.setEcts(rs.getInt(4));
                tempCourse.setStartDate(rs.getDate(5));
                allCourses.add(tempCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    @Override
    public boolean update(Course course){
        try{
            String update = "UPDATE courses SET cName=?, loc=?, ects=?, startDate=? WHERE course_id=?";
            PreparedStatement updateStatement = conn.prepareStatement(update);
            updateStatement.setString(1, course.getCName());
            updateStatement.setString(2, course.getLoc());
            updateStatement.setInt(3, course.getEcts());
            updateStatement.setDate(4, new java.sql.Date(course.getStartDate().getTime()));
            updateStatement.setInt(5, course.getId());
            updateStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE course_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
