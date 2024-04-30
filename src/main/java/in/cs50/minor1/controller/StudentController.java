package in.cs50.minor1.controller;

import in.cs50.minor1.model.Student;
import in.cs50.minor1.request.StudentCreateRequest;
import in.cs50.minor1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public Student createStudent(@RequestBody StudentCreateRequest studentCreateRequest){

        //validation check --


        return studentService.createStudent(studentCreateRequest);

    }
}
