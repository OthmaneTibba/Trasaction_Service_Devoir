package com.example.transaction.dto;

import com.example.transaction.entites.Transaction;
import com.example.transaction.entites.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactiobByCategory {
    private List<Transaction> transactions;
    private Category category;
    private  int number;

}
