package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.StudentRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private IStudentRepository studentRepository;

    public StudentController() {
        studentRepository = new StudentRepositoryImpl();
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("students", studentRepository.readAll());
        return "index";
    }

    //Very simple prototype of GET-request with parameter
    //https://www.baeldung.com/spring-request-param
    //TODO Direct to detailed view of student
    @GetMapping("/student")
    @ResponseBody
    public String getStudentByParameter(@RequestParam int id) {
        Student stu = studentRepository.read(id);
        return "This id belongs to " + stu.getFirstName() + " " + stu.getLastName();
    }

    @RequestMapping(value = "/student/detail/{id}", method = RequestMethod.GET)
    public String getStudentByDetailParam(@PathVariable int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("student", stu);
        return "/student/detail";
    }

    @RequestMapping(value = "/student/delete/{id}", method = RequestMethod.GET)
    public String getStudentByDeleteParam(@PathVariable int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("student", stu);
        return "/student/delete";
    }

    @GetMapping("/student/delete/yes/{id}")
    public String delStudent(@PathVariable int id){
        studentRepository.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/student/edit/{id}", method = RequestMethod.GET)
    public String getEditById(@PathVariable int id, Model model) {
        Student stu = studentRepository.read(id);
        model.addAttribute("student", stu);
        return "/student/edit";
    }

    @PostMapping("/student/edited")
    public String editStudents(Student student){
        Student studentToEdit = student;
        boolean found = false;

        for (Student student2 : studentRepository.readAll()){
            if (student2.getId() == student.getId()){
                studentToEdit = student2;
                found = true;
                break;
            }
        }
        if (!found){
            return "redirect:/";
        }
        if (!student.getFirstName().equals("")){
            studentToEdit.setFirstName(student.getFirstName());
        }
        if (!student.getLastName().equals("")){
            studentToEdit.setLastName(student.getLastName());
        }
        if (student.getEnrollmentDate() != null){
            studentToEdit.setEnrollmentDate(student.getEnrollmentDate());
        }
        if (!student.getCpr().equals("")){
            studentToEdit.setCpr(student.getCpr());
        }
        studentRepository.update(studentToEdit);
        return "redirect:/";
    }

    @GetMapping("/student/create")
    public String create(){
        return "/student/create";
    }

    @PostMapping("/student/created")
    public String createStudent(Student student){
        studentRepository.create(student);
        return "redirect:/";
    }

}