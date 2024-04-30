package in.cs50.minor1.controller;

import in.cs50.minor1.model.Book;
import in.cs50.minor1.model.FilterType;
import in.cs50.minor1.model.Operator;
import in.cs50.minor1.request.BookCreateRequest;
import in.cs50.minor1.response.GenericResponse;
import in.cs50.minor1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public Book createBook(@RequestBody BookCreateRequest bookCreateRequest){

        //validation check --

        return bookService.createBook(bookCreateRequest);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> filter(@RequestParam("filterBy") FilterType filterBy,
                                            @RequestParam("operator")Operator operator,
                                            @RequestParam("value") String value){

        List<Book> list= bookService.filter(filterBy, operator,  value);
        GenericResponse<List<Book>> response = new GenericResponse<>(list,"","success","200");
        ResponseEntity entity = new ResponseEntity<>(response, HttpStatus.OK);
        return entity;


    }
}
