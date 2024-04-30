package in.cs50.minor1.service;

import in.cs50.minor1.exception.TxnException;
import in.cs50.minor1.model.*;
import in.cs50.minor1.repository.TxnRepository;
import in.cs50.minor1.request.TxnCreateRequest;
import in.cs50.minor1.request.TxnReturnRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {
    @Autowired
    private TxnRepository txnRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private StudentService studentService;

    @Value("${student.valid.days}")
    private String validUpto;

    @Value("${student.delayed.finePerDay}")
    private String finePerDay;

    private Student filterStudent(StudentFilterType type, Operator operator, String value) throws TxnException {
        List<Student> studentList = studentService.filter(type, operator, value);
        if(studentList == null || studentList.isEmpty()){
            throw new TxnException("Student not from our library");
        }

        Student studentFromDb = studentList.get(0);
        return studentFromDb;
    }

    private Book filterBook(FilterType type, Operator operator, String value) throws TxnException {
        List<Book> bookList = bookService.filter(type, operator, value);
        if(bookList == null || bookList.isEmpty()){
            throw new TxnException("Book not from our library");
        }

        Book bookFromLib = bookList.get(0);
        return bookFromLib;
    }
    private int calculateSettlementAmount(Txn txn) {

       long issuedTime = txn.getCreatedOn().getTime();
       long returnTime = System.currentTimeMillis();

       long timeDiff = returnTime-issuedTime;
       int daysPassed = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

       if(daysPassed>Integer.valueOf(validUpto)){
           int fineAmount = (daysPassed- Integer.valueOf(validUpto))*Integer.valueOf(finePerDay);
           return txn.getPaidAmount()-fineAmount;
       }

        return txn.getPaidAmount();
    }


    @Transactional(rollbackOn={TxnException.class})
    public String create(TxnCreateRequest txnCreateRequest) throws TxnException {

        //if student is valid or not
        Student studentFromDb = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnCreateRequest.getStudentContact());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnCreateRequest.getBookNo());

        if(bookFromLib.getStudent()!= null){
            throw new TxnException("Book already assigned to someone");

        }
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder().
                student(studentFromDb).
                book(bookFromLib).
                txnId(txnId).
                paidAmount(txnCreateRequest.getAmount()).
                status(TxnStatus.ISSUED).
                build();

        txn = txnRepository.save(txn);
        bookFromLib.setStudent(studentFromDb);
        bookService.saveUpdate(bookFromLib);
        return txn.getTxnId();
    }
    @Transactional(rollbackOn={TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {
        Student studentFromDb = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnReturnRequest.getStudentContact());
        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnReturnRequest.getBookNo());

        if(bookFromLib.getStudent() != null && bookFromLib.getStudent().equals(studentFromDb)){
           Txn txnFromDb =  txnRepository.findByTxnId(txnReturnRequest.getTxnId());
           if(txnFromDb==null){
               throw new TxnException("no txn has been found with this txnId");
           }
           int amount = calculateSettlementAmount(txnFromDb);
           if(amount == txnFromDb.getPaidAmount()){
               txnFromDb.setStatus(TxnStatus.RETURNED);
           }
           else {
               txnFromDb.setStatus(TxnStatus.FINED);
           }
           txnFromDb.setPaidAmount(amount);

           //now update the book bby marking null;
            bookFromLib.setStudent(null);
            bookService.saveUpdate(bookFromLib);
            return amount;
        }
        else {
            throw new TxnException("Book is either not assigned or maybe assigned to anyone else");
        }
    }


}
