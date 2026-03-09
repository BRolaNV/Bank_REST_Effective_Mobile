package com.example.bankcards.controller;


import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("")
    public void transfer(@Valid @RequestBody TransferRequest request) {
        transferService.transfer(request);
    }
}
