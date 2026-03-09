package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.CardNotActiveException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public TransferService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public void transfer(TransferRequest request) {

        AppUser appUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        Long userId = appUser.getId();

        Card fromCard = cardRepository.findByIdAndUserId(request.getFromCardId(), userId).orElseThrow(()
                -> new CardNotFoundException("Card not found"));

        Card toCard = cardRepository.findByIdAndUserId(request.getToCardId(), userId).orElseThrow(()
                -> new CardNotFoundException("Card not found"));

        if (fromCard.getStatus() == CardStatus.ACTIVE &&
                toCard.getStatus() == CardStatus.ACTIVE) {

            if (fromCard.getBalance().compareTo(request.getAmount()) >= 0) {

                fromCard.setBalance(fromCard.getBalance().subtract(request.getAmount()));
                cardRepository.save(fromCard);

                toCard.setBalance(toCard.getBalance().add(request.getAmount()));
                cardRepository.save(toCard);

            } else {
                throw new InsufficientFundsException("Insufficient funds");
            }

        } else {
            throw new CardNotActiveException("Card is not active");
        }
    }
}
