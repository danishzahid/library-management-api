package in.cs50.minor1.request;

import in.cs50.minor1.model.Student;
import in.cs50.minor1.model.StudentType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateRequest {

    private String name;


    private  String email;

    private String phoneNo;

    private String address;

    public Student toStudent() {
        return Student.builder().
                name(this.name).
                email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                status(StudentType.ACTIVE).
                build();
    }
}
