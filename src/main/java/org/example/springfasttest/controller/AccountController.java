package org.example.springfasttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.springfasttest.dto.TransferRequest;
import org.example.springfasttest.model.Account;
import org.example.springfasttest.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest transferRequest) {
        transferService.transfer(
            transferRequest.getIdSender(),
            transferRequest.getIdReceiver(),
            transferRequest.getAmount()
        );
    }

    @GetMapping("/accounts")
    public List<Account> accounts(
        @RequestParam(required = false) String name
    ) {
        if (name != null) {
            return transferService.getAccountsByName(name);
        } else {
            return transferService.accounts();
        }
    }

}
