package org.example.springfasttest.service;

import lombok.RequiredArgsConstructor;
import org.example.springfasttest.exception.AccountNotFoundException;
import org.example.springfasttest.model.Account;
import org.example.springfasttest.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;


    @Transactional
    public void transfer(long idSender, long idReceiver, BigDecimal amount) {
        Account sender = accountRepository.findById(idSender).orElseThrow(() -> new AccountNotFoundException());
        Account receiver = accountRepository.findById(idReceiver).orElseThrow(() -> new AccountNotFoundException());

        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);
        BigDecimal receiverNewAmount = receiver.getAmount().add(amount);

        accountRepository.updateAccount(idSender, senderNewAmount);
        accountRepository.updateAccount(idReceiver, receiverNewAmount);
    }

    public List<Account> accounts() {
        return accountRepository.findAllAccounts();
    }

    public List<Account> getAccountsByName(String name) {
        return accountRepository.findAccountsByName(name);
    }

}