package com.example.bankcards.controller;


import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/transfers")
@Tag(name = "Transfer Controller", description = "Controller for managing transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    @PostMapping("")
    @Operation(summary = "Transfer money between cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid transfer request"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        transferService.transfer(request);
        return ResponseEntity.noContent().build();
    }
}
