package com.example.bankcards.controller;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cards")
@Tag(name = "Admin Card Controller", description = "Controller for managing cards by admin")
public class AdminCardController {

    private final CardService cardService;

    public AdminCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("")
    @Operation(summary = "Create a new card", description = "Creates a new card with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid card number"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CardResponse createCard(@Valid @RequestBody CardRequest request) {
        CardResponse card = cardService.createCard(request);
        return card;
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate a card", description = "Activates a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card activated successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CardResponse activateCard(@PathVariable Long id) {
        CardResponse card = cardService.updateCard(id, CardStatus.ACTIVE);
        return card;
    }

    @PutMapping("/{id}/block")
    @Operation(summary = "Block a card", description = "Blocks a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card blocked successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public CardResponse blockCard(@PathVariable Long id) {
        CardResponse card = cardService.updateCard(id, CardStatus.BLOCKED);
        return card;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a card", description = "Deletes a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    @Operation(summary = "Get all cards", description = "Returns a list of all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cards retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public List<CardResponse> getAllCards() {
        return cardService.getAllCards();
    }

}
