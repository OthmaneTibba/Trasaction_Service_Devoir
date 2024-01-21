package com.example.transaction.dto;

import com.example.transaction.entites.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private String name;
    private double montant;
    private Type type;
    private String currency_name;
    private int categoryId;
}
