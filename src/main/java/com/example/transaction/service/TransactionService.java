package com.example.transaction.service;

import com.example.transaction.dto.Category;
import com.example.transaction.dto.TransactiobByCategory;
import com.example.transaction.dto.TransactionDto;
import com.example.transaction.dto.TransactionResponseDto;
import com.example.transaction.entites.Transaction;
import com.example.transaction.exception.CategoryNotFound;
import com.example.transaction.external.client.CategoryService;
import com.example.transaction.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    private   String URL="https://category-service-cu.azurewebsites.net/api/category/id/";


    public Transaction saveTransaction(TransactionDto transaction) {
        System.out.println(transaction.getCategoryId());
        try {
            URL += transaction.getCategoryId();
            Category categoryResponseEntity = restTemplate.getForEntity(URL, Category.class).getBody();
log.info("=====> :{}",categoryResponseEntity );
            if (categoryResponseEntity == null) {
                throw new CategoryNotFound("Category not found", HttpStatus.NOT_FOUND.toString());
            }

            Transaction newTransaction = Transaction.builder()
                    .name(transaction.getName())
                    .date_created(Instant.now())
                    .currency_name(transaction.getCurrency_name())
                    .categoryId(categoryResponseEntity.getId())
                    .montant(transaction.getMontant())
                    .type(transaction.getType())
                    .build();

            return transactionRepository.save(newTransaction);

        } catch (RestClientException ex) {
            // Handle RestClientException (e.g., connection issues, timeouts)
            throw new RuntimeException("Error calling category service: " + ex.getMessage(), ex);
        }
    }

    public List<Transaction> getAllTransaction(){

        List<Transaction> transactions = transactionRepository.findAll();
        return  transactions;
    }
    public List<TransactionResponseDto> getAllTransactionm(){

        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionResponseDto> transactionDtos = new ArrayList<>();
        String url = "";
        for(Transaction transaction: transactions){
            url = "https://category-service-cu.azurewebsites.net/api/category/id/" + transaction.getCategoryId();
            Category categoryResponseEntity = restTemplate.getForEntity(
                    url,
                    Category.class

            ).getBody();
            log.info("=====> :{}",categoryResponseEntity );
            if (categoryResponseEntity == null) {
                throw new CategoryNotFound("Category not found", HttpStatus.NOT_FOUND.toString());
            }
            TransactionResponseDto transactionDto = TransactionResponseDto.builder()
                    .type(transaction.getType())
                    .name(transaction.getName())
                    .montant(transaction.getMontant())
                    .currency_name(transaction.getCurrency_name())
                    .category(categoryResponseEntity)
                    .build();
            transactionDtos.add(transactionDto);
        }
        log.info("transaction  ==========: {}", transactionDtos);
        transactionDtos.forEach(System.out::println);
        return new ResponseEntity<>(transactionDtos, HttpStatus.OK).getBody();
    }


   //chaque category combien de transaction
   public ResponseEntity<List<TransactiobByCategory>> numberTransactionByCategory() {
       try {
           List<TransactionResponseDto> transactionDtos = getAllTransactionm();
           List<TransactiobByCategory> transact = new ArrayList<>();

           List<Category> categories = List.of(restTemplate.getForEntity(
                   URL + "/all",
                   Category[].class).getBody());

           for (Category category : categories) {
               List<Transaction> transactions = transactionRepository
                       .findAll()
                       .stream()
                       .filter(c -> c.getCategoryId() == category.getId())
                       .collect(Collectors.toList());

               TransactiobByCategory tr = TransactiobByCategory.builder()
                       .number((int) transactions.size())
                       .category(category)
                       .transactions(transactions)
                       .build();
               transact.add(tr);
           }

           log.info("transaction  ==========: {}", transact);
           transact.forEach(System.out::println);

           return ResponseEntity.ok(transact);
       } catch (RestClientException ex) {
           log.error("Error while fetching categories: {}", ex.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
    //combien de transaction par jour

    public Map<LocalDate, Long> getTransactionsCountByDay() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        return allTransactions.stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getDate_created().atZone(ZoneId.systemDefault()).toLocalDate(),
                        Collectors.counting()
                ));
    }
}
