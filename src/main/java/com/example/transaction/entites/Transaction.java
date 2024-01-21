package com.example.transaction.entites;

import com.example.transaction.dto.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double montant;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Instant date_created;
    private String currency_name;
    private int categoryId;

}
