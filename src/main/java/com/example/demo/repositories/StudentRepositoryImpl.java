package com.example.demo.repositories;

import com.example.demo.models.Student;
import com.example.demo.util.DatabaseConnectionManager;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements IStudentRepository {
    private Connection conn;

    public StudentRepositoryImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public boolean create(Student student) {
        try {
            PreparedStatement createStu = conn.prepareStatement("INSERT INTO students (firstName, lastName, enrollmentDate, cpr) VALUES (?, ?, ?, ?);");
            createStu.setString(1, student.getFirstName());
            createStu.setString(2, student.getLastName());
            createStu.setDate(3, new java.sql.Date(student.getEnrollmentDate().getTime()));
            createStu.setString(4, student.getCpr());
            createStu.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student read(int id) {
        Student studentToReturn = new Student();
        try {
            PreparedStatement getSingleStudent = conn.prepareStatement("SELECT * FROM students WHERE students_id=?");
            getSingleStudent.setInt(1, id);
            ResultSet rs = getSingleStudent.executeQuery();
            while(rs.next()){
                studentToReturn = new Student();
                studentToReturn.setId(rs.getInt(1));
                studentToReturn.setFirstName(rs.getString(2));
                studentToReturn.setLastName(rs.getString(3));
                studentToReturn.setEnrollmentDate(rs.getDate(4));
                studentToReturn.setCpr(rs.getString(5));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return studentToReturn;
    }

    @Override
    public List<Student> readAll() {
        List<Student> allStudents = new ArrayList<Student>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Student tempStudent = new Student();
                tempStudent.setId(rs.getInt(1));
                tempStudent.setFirstName(rs.getString(2));
                tempStudent.setLastName(rs.getString(3));
                tempStudent.setEnrollmentDate(rs.getDate(4));
                tempStudent.setCpr(rs.getString(5));
                allStudents.add(tempStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allStudents;
    }

    @Override
    public List<Student> getAtt(int id){
        List<Student> allStudents = new ArrayList<Student>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT students_id FROM courseAtt where course_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int tempId = rs.getInt(1);
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM students where students_id=?");
                ps2.setInt(1, tempId);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    Student tempStudent = new Student();
                    tempStudent.setId(rs2.getInt(1));
                    tempStudent.setFirstName(rs2.getString(2));
                    tempStudent.setLastName(rs2.getString(3));
                    tempStudent.setEnrollmentDate(rs2.getDate(4));
                    tempStudent.setCpr(rs2.getString(5));
                    allStudents.add(tempStudent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allStudents;
    }

    @Override
    public boolean removeCourse(int stuId, int courseId){
        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM courseAtt WHERE students_id=? AND course_id=?");
            ps.setInt(1, stuId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Student> getAddStu(int courseId){
        List<Student> allStu = new ArrayList<Student>();
        List<Student> attStu = new ArrayList<Student>();
        List<Student> stuToAdd = new ArrayList<Student>();

        for (Student stu : readAll()){
            allStu.add(stu);
        }

        for (Student stu : getAtt(courseId)){
            attStu.add(stu);
        }

        while (attStu.size() < allStu.size()){
            attStu.add(new Student());
        }

        for (Student stu : allStu){
            for (Student stu2 : attStu) {
                if (stu.getId() == stu2.getId()){
                    attStu.remove(stu2);
                    break;
                }
                stuToAdd.add(stu);
                break;
            }
        }
        return stuToAdd;
    }

    @Override
    public boolean addStu(int id, int course_id) {
        try {
            PreparedStatement createStu = conn.prepareStatement("INSERT INTO courseAtt (students_id, course_id) VALUES (?, ?)");
            createStu.setInt(1, id);
            createStu.setInt(2, course_id);
            createStu.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        try{
            String update = "UPDATE students SET firstName=?, lastName=?, enrollmentDate=?, cpr=? WHERE students_id=?";
            PreparedStatement updateStatement = conn.prepareStatement(update);
            updateStatement.setString(1, student.getFirstName());
            updateStatement.setString(2, student.getLastName());
            updateStatement.setDate(3, new java.sql.Date(student.getEnrollmentDate().getTime()));
            updateStatement.setString(4, student.getCpr());
            updateStatement.setInt(5,student.getId());
            updateStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE students_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
