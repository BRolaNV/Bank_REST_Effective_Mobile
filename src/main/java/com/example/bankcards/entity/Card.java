package com.example.bankcards.entity;

import com.example.bankcards.util.CardNumberEncryptor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Table(name = "cards")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "card_number")
    @Convert(converter = CardNumberEncryptor.class)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private BigDecimal balance;

    @Column(name = "expire_date")
    private String expireDate;

    @Column(name = "owner_name")
    private String ownerName;
}
