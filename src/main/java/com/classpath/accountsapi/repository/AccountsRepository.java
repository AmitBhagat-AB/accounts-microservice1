package com.classpath.accountsapi.repository;

import com.classpath.accountsapi.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

    List<Account> findByBalanceGreaterThan(double accountBalance, Pageable page);

}