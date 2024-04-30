package in.cs50.minor1.service;

import in.cs50.minor1.model.*;
import in.cs50.minor1.repository.StudentRepository;
import in.cs50.minor1.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        List<Student> studentList = studentRepository.findByPhoneNo(studentCreateRequest.getPhoneNo());
        Student studentFromDb=null;
        if(studentList==null || studentList.isEmpty()){
           studentFromDb= studentRepository.save(studentCreateRequest.toStudent());
           return studentFromDb;
        }
        studentFromDb = studentList.get(0);
        return studentFromDb;


    }
    public List<Student> filter(StudentFilterType filterBy, Operator operator, String value) {

        switch (operator) {
            case EQUALS:
                switch (filterBy) {
                    case CONTACT:
                        return studentRepository.findByPhoneNo(value);
                }
            default: return new ArrayList<>();
        }
    }
}
