package in.cs50.minor1.controller;

import in.cs50.minor1.exception.TxnException;
import in.cs50.minor1.request.TxnCreateRequest;
import in.cs50.minor1.request.TxnReturnRequest;
import in.cs50.minor1.response.GenericResponse;
import in.cs50.minor1.service.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<String>> createTxn(@RequestBody @Valid TxnCreateRequest txnCreateRequest) throws TxnException {
       String txnId = txnService.create(txnCreateRequest);
       GenericResponse<String> response = new GenericResponse<>(txnId,"","success","200");
       ResponseEntity entity = new ResponseEntity<>(response, HttpStatus.OK);
       return entity;
    }
    @PutMapping("/return")
    public int returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException {
        return txnService.returnBook(txnReturnRequest);
    }
}
