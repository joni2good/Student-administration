package com.example.demo.controllers;

import com.example.demo.models.Course;
import com.example.demo.repositories.CourseRepositoryImpl;
import com.example.demo.repositories.ICoursesRepository;
import com.example.demo.repositories.IStudentRepository;
import com.example.demo.repositories.StudentRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    private ICoursesRepository courseRepository;
    private IStudentRepository studentRepository;

    public CourseController() {
        courseRepository = new CourseRepositoryImpl();
        studentRepository = new StudentRepositoryImpl();
    }

    @GetMapping("/course/course_index")
    public String index(Model model){
        model.addAttribute("courses", courseRepository.readAll());
        return "/course/course_index";
    }

    @RequestMapping(value = "/course/cDetail/{id}", method = RequestMethod.GET)
    public String getCourseByDetailParam(@PathVariable int id, Model model) {
        Course course = courseRepository.read(id);
        model.addAttribute("course", course);
        model.addAttribute("students", studentRepository.getAtt(id));
        return "/course/cDetail";
    }

    @RequestMapping(value = "/course/cDelete/{id}", method = RequestMethod.GET)
    public String delCourseByDeleteParam(@PathVariable int id, Model model) {
        Course course = courseRepository.read(id);
        model.addAttribute("course", course);
        return "/course/cDelete";
    }

    @GetMapping("/course/cDelete/yes/{id}")
    public String delCourse(@PathVariable int id){
        courseRepository.delete(id);
        return "redirect:/course/course_index";
    }

    @RequestMapping(value = "/course/cRemove/{courseId}/{id}", method = RequestMethod.GET)
    public String remStuByRemoveParam(@PathVariable int id,@PathVariable int courseId, Model model) {
        model.addAttribute("student", studentRepository.read(id));
        model.addAttribute("course", courseRepository.read(courseId));
        return "/course/cRemove";
    }

    @GetMapping("/course/cRemove/yes/{courseId}/{id}")
    public String remStu(@PathVariable int id, @PathVariable int courseId){
        studentRepository.removeCourse(id, courseId);
        return "redirect:/course/cDetail/{courseId}";
    }

    @RequestMapping(value = "/course/cAddStu/{courseId}", method = RequestMethod.GET)
    public String addStuByAddParam(@PathVariable int courseId, Model model) {
        model.addAttribute("students", studentRepository.getAddStu(courseId));
        model.addAttribute("course", courseRepository.read(courseId));
        return "/course/cAddStu";
    }

    @GetMapping("/course/cAddStu/add/{courseId}/{id}")
    public String addStu(@PathVariable int id, @PathVariable int courseId){
        studentRepository.addStu(id, courseId);
        return "redirect:/course/cAddStu/{courseId}";
    }

    @RequestMapping(value = "/course/cEdit/{id}", method = RequestMethod.GET)
    public String getEditById(@PathVariable int id, Model model) {
        Course course = courseRepository.read(id);
        model.addAttribute("course", course);
        return "/course/cEdit";
    }

    @PostMapping("/course/edited")
    public String editCourse(Course course){
        Course courseToEdit = course;
        boolean found = false;

        for (Course course2 : courseRepository.readAll()){
            if (course2.getId() == course.getId()){
                courseToEdit = course2;
                found = true;
                break;
            }
        }
        if (!found){
            return "redirect:/course/course_index";
        }
        if (!course.getCName().equals("")){
            courseToEdit.setCName(course.getCName());
        }
        if (!course.getLoc().equals("")){
            courseToEdit.setLoc(course.getLoc());
        }
        if (course.getEcts()  != 0){
            courseToEdit.setEcts(course.getEcts());
        }
        if (course.getStartDate() != null){
            courseToEdit.setStartDate(course.getStartDate());
        }
        courseRepository.update(courseToEdit);
        return "redirect:/course/course_index";
    }

    @GetMapping("/course/cCreate")
    public String create(){
        return "/course/cCreate";
    }

    @PostMapping("/course/created")
    public String createStudent(Course course){
        courseRepository.create(course);
        return "redirect:/course/course_index";
    }

}