package com.example.transaction.controller;

import com.example.transaction.dto.TransactiobByCategory;
import com.example.transaction.dto.TransactionDto;
import com.example.transaction.dto.TransactionResponseDto;
import com.example.transaction.entites.Transaction;
import com.example.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<Transaction> saveTreansaction(@RequestBody TransactionDto transaction){
        return  new ResponseEntity<>(transactionService.saveTransaction(transaction), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponseDto>> all(){
        return  new ResponseEntity<>(transactionService.getAllTransactionm(), HttpStatus.CREATED);

    }
    @GetMapping("/test")
    public ResponseEntity<List<TransactiobByCategory>> alls() {
        return transactionService.numberTransactionByCategory();
    }
    @GetMapping("/test1")

    public Map<LocalDate, Long> getTransactionsCountByDay() {
        return  transactionService.getTransactionsCountByDay();
    }

    }
