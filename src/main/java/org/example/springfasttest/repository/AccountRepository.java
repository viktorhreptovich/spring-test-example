package org.example.springfasttest.repository;

import org.example.springfasttest.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("SELECT * FROM account")
    List<Account> findAllAccounts();

    @Modifying
    @Query("UPDATE account SET amount = :amount WHERE id = :id")
    void updateAccount(long id, BigDecimal amount);

    @Query("SELECT * FROM account WHERE name = :name")
    List<Account> findAccountsByName(String name);

}
