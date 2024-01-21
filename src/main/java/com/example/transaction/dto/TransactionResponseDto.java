package com.example.transaction.dto;

import com.example.transaction.entites.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionResponseDto {
    private String name;
    private double montant;
    private Type type;
    private String currency_name;
    private Category category;
}
