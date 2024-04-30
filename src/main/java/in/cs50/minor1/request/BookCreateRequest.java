package in.cs50.minor1.request;

import in.cs50.minor1.model.Author;
import in.cs50.minor1.model.Book;
import in.cs50.minor1.model.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Book Name must not be blank")
    private String name;

    @NotBlank(message = "Book No must not be blank")
    private String bookNo;

    @Positive
    private Integer cost;

    private BookType type;

    private String authorName;

    private String authorEmail;

    public Author toAuthor() {
        return Author.builder().
                name(this.authorName).
                email(this.authorEmail)
                .build();
    }

    public Book toBook() {
        return Book.builder().
                name(this.name).
                bookNo(this.bookNo).
                cost(this.cost).
                type(this.type).
                build();

    }
}
