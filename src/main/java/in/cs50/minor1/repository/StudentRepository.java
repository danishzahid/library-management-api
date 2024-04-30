package in.cs50.minor1.repository;

import in.cs50.minor1.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByPhoneNo(String phoneNo);
}
