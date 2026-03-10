package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.CardNotActiveException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferService transferService;

    AppUser appUser;
    Card fromCard;
    Card toCard;

    @BeforeEach
    public void setUp() {

        appUser = AppUser.builder()
                .username("user")
                .password("password")
                .id(1L)
                .build();

        fromCard = Card.builder()
                .user(appUser)
                .balance(BigDecimal.valueOf(100))
                .status(CardStatus.ACTIVE)
                .expireDate("2026-12-31")
                .ownerName("John Doe")
                .cardNumber("1234123412341234")
                .id(1L)
                .build();

        toCard = Card.builder()
                .user(appUser)
                .balance(BigDecimal.valueOf(0))
                .status(CardStatus.ACTIVE)
                .expireDate("2026-12-31")
                .ownerName("John Doe")
                .cardNumber("1234123412341235")
                .id(2L)
                .build();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void transfer_success() {

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(appUser));
        when(cardRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findByIdAndUserId(2L, 1L)).thenReturn(Optional.of(toCard));

        TransferRequest request = TransferRequest.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(BigDecimal.valueOf(50))
                .build();

        transferService.transfer(request);

        assertEquals(BigDecimal.valueOf(50), fromCard.getBalance());
        assertEquals(BigDecimal.valueOf(50), toCard.getBalance());
    }

    @Test
    public void transfer_insufficientFunds() {

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(appUser));
        when(cardRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findByIdAndUserId(2L, 1L)).thenReturn(Optional.of(toCard));

        TransferRequest request = TransferRequest.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(BigDecimal.valueOf(110))
                .build();

        assertThrows(InsufficientFundsException.class, () -> transferService.transfer(request));
    }

    @Test
    public void transfer_cardNotFound() {

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(appUser));
        when(cardRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(fromCard));

        TransferRequest request = TransferRequest.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(BigDecimal.valueOf(110))
                .build();

        assertThrows(CardNotFoundException.class, () -> transferService.transfer(request));
    }

    @Test
    public void transfer_cardNotActive() {

        toCard.setStatus(CardStatus.BLOCKED);

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(appUser));
        when(cardRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findByIdAndUserId(2L, 1L)).thenReturn(Optional.of(toCard));

        TransferRequest request = TransferRequest.builder()
                .fromCardId(1L)
                .toCardId(2L)
                .amount(BigDecimal.valueOf(110))
                .build();

        assertThrows(CardNotActiveException.class, () -> transferService.transfer(request));
    }
}
