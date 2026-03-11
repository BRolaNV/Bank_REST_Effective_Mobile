package com.example.bankcards.service;

import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    private Card card;
    private AppUser appUser;


    @BeforeEach
    public void setUp() {

        appUser = AppUser.builder()
                .username("user")
                .password("password")
                .id(1L)
                .build();

        card = Card.builder()
                .user(appUser)
                .balance(BigDecimal.valueOf(100))
                .status(CardStatus.ACTIVE)
                .expireDate("2026-12-31")
                .ownerName("John Doe")
                .cardNumber("1234123412341234")
                .id(1L)
                .build();
    }


    @Test
    public void blockCard_success() {

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any())).thenReturn(card);

        cardService.updateCard(1L, CardStatus.BLOCKED);

        assertEquals(CardStatus.BLOCKED, card.getStatus());
    }


    @Test
    public void activateCard_success() {

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any())).thenReturn(card);

        card.setStatus(CardStatus.BLOCKED);

        cardService.updateCard(1L, CardStatus.ACTIVE);

        assertEquals(CardStatus.ACTIVE, card.getStatus());
    }
}

