package com.classpath.accountsapi.repository;

import com.classpath.accountsapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

    Set<Account> findByBalanceGreaterThan(double accountBalance);

}