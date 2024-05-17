package org.example.springfasttest;

import org.example.springfasttest.exception.AccountNotFoundException;
import org.example.springfasttest.model.Account;
import org.example.springfasttest.repository.AccountRepository;
import org.example.springfasttest.service.TransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferServiceUnitTests {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransferService transferService;

    @Test
    @DisplayName("Test successful transfer")
    public void testTransferSuccess() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setAmount(BigDecimal.valueOf(1000));

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setAmount(BigDecimal.valueOf(1000));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(receiver.getId())).willReturn(Optional.of(receiver));

        transferService.transfer(
            sender.getId(),
            receiver.getId(), BigDecimal.valueOf(100)
        );

        verify(accountRepository).updateAccount(1L, BigDecimal.valueOf(900));
        verify(accountRepository).updateAccount(2L, BigDecimal.valueOf(1100));
    }


    @Test
    @DisplayName("Test failed transfer when sender not found")
    public void testTransferFailedWhenSenderNotFound() {
        given(accountRepository.findById(1L)).willReturn(Optional.empty());

        Executable transfer = () -> transferService.transfer(
            1L,
            2L,
            BigDecimal.valueOf(100)
        );

        assertThrows(AccountNotFoundException.class, transfer);
    }

    @Test
    @DisplayName("Test failed transfer when receiver not found")
    public void testTransferFailedWhenReceiverNotFound() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setAmount(BigDecimal.valueOf(1000));
        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        Executable transfer = () -> transferService.transfer(
            sender.getId(),
            2L,
            BigDecimal.valueOf(100)
        );

        assertThrows(AccountNotFoundException.class, transfer);
    }

}
