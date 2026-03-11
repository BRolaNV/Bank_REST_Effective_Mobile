package com.example.bankcards.service;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.CardNotActiveException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardMaskUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;

    }


    public CardResponse createCard(CardRequest cardRequest) {

        Card saved = cardRepository.save(Card.builder()
                        .cardNumber(cardRequest.getCardNumber().replaceAll("[\\s-]+", ""))
                        .status(CardStatus.ACTIVE)
                        .ownerName(cardRequest.getOwnerName())
                        .balance(BigDecimal.valueOf(0))
                        .expireDate(cardRequest.getExpireDate())
                        .user(userRepository.findById(cardRequest.getUserId()).orElseThrow(()
                                -> new UserNotFoundException("User not found")))
                .build());

        return toResponse(saved);
    }


    public void deleteCard(Long cardId) {

        Card card = cardRepository.findById(cardId).orElseThrow(()
                -> new CardNotFoundException("Card not found"));

        cardRepository.delete(card);

    }


    public CardResponse updateCard(Long cardId, CardStatus status) {

        Card card = cardRepository.findById(cardId).orElseThrow(()
                -> new CardNotFoundException("Card not found"));

        card.setStatus(status);
        Card saved = cardRepository.save(card);

        return toResponse(saved);
    }


    public List<CardResponse> getAllCards() {
        return cardRepository.findAll().stream().map(this::toResponse).toList();
    }


    public Page<CardResponse> getUsersCards(CardStatus status, int page, int size) {

        AppUser appUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        Long userId = appUser.getId();

        Pageable pageable = PageRequest.of(page, size);

        if (status == null) {
            return cardRepository.findByUserId(userId, pageable).map(this::toResponse);
        } else {
            return cardRepository.findByUserIdAndStatus(userId, status, pageable).map(this::toResponse);
        }
    }


    public BigDecimal getCardBalance(Long cardId) {

        AppUser appUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        Long userId = appUser.getId();

        Optional<Card> card = cardRepository.findByIdAndUserId(cardId, userId);

        return card.orElseThrow(() -> new CardNotFoundException("Card not found")).getBalance();
    }


    public CardResponse requestBlock(Long cardId){

        AppUser appUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        Long userId = appUser.getId();

        Card card = cardRepository.findByIdAndUserId(cardId, userId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        if (card.getStatus() == CardStatus.ACTIVE){

            card.setStatus(CardStatus.BLOCKED);
            Card saved = cardRepository.save(card);

            return toResponse(saved);
        } else {
            throw new CardNotActiveException("Card is not active");
        }
    }


    private CardResponse toResponse(Card card) {

        return CardResponse.builder()
                .id(card.getId())
                .maskedCardNumber(CardMaskUtil.maskCardNumber(card.getCardNumber()))
                .ownerName(card.getOwnerName())
                .expireDate(card.getExpireDate())
                .status(card.getStatus())
                .balance(card.getBalance())
                .build();
    }
}
