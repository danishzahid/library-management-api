package in.cs50.minor1.repository;

import in.cs50.minor1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //3 ways to write a query in repository
    //1st-no hibernate involved, native sql query ie why author with small case
    @Query(value = "select * from author where email =:email", nativeQuery = true)
    Author getAuthor(String email);

    //2nd way- jpql, hibernate involved
    @Query("select a from Author a where a.email =:email")
    Author getAuthorWithoutNative(String email);

    //3rd way - direct by using hibernate
    Author findByEmail(String email);



}
