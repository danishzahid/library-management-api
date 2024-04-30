package in.cs50.minor1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;


    @Column(length = 30, unique = true)
    private  String email;

    @Column(length = 15, unique = true,nullable = false)
    private String phoneNo;

    private String address;

    @CreationTimestamp
    private Date createdOn;

    @CreationTimestamp
    private Date updatedOn;

    @Enumerated(value = EnumType.STRING)
    private StudentType status;


    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Book> list;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Txn> txnList;

}
