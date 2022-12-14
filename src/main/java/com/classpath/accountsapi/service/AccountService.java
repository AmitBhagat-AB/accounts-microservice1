package com.classpath.accountsapi.service;

import com.classpath.accountsapi.model.Account;
import com.classpath.accountsapi.model.Loan;
import com.classpath.accountsapi.model.Transaction;
import com.classpath.accountsapi.repository.AccountsRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountsRepository accountsRepository;

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    private final LoanFeignClient feignClient;

    private final Source source;


    public Transaction checkBalance(long accountId){
        Account account = this.accountsRepository.findById(accountId)
                                                    .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        Transaction transaction = new Transaction();
        transaction.setAmount(account.getBalance());
        transaction.setAccountId(account.getAccountId());
        transaction.setType("CHECK_BALANCE");
        return transaction;
    }

    public Transaction deposit(long accountId, Transaction transaction){
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));
        double updatedAccountBalance = account.getBalance() + transaction.getAmount();
        account.setBalance(updatedAccountBalance);
        this.accountsRepository.save(account);
        Transaction transactionOut = new Transaction();
        transactionOut.setAmount(account.getBalance());
        transactionOut.setAccountId(account.getAccountId());
        transactionOut.setAccountId(account.getAccountId());
        transactionOut.setType("DEPOSIT");
        transactionOut.setNotes(transaction.getNotes());
        return transactionOut;
    }

    public Transaction withdraw(long accountId, Transaction transaction) {
        Account account = this.accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with Account id does not exists"));

        double currentAccountBalance = account.getBalance();
        double withdrawalAmount = transaction.getAmount();
        if ((currentAccountBalance - withdrawalAmount) > Account.MIN_BALANCE){
            currentAccountBalance = currentAccountBalance - withdrawalAmount;
            account.setBalance(currentAccountBalance);
            this.accountsRepository.save(account);
            Transaction transactionOut = new Transaction();
            transactionOut.setAmount(withdrawalAmount);
            transactionOut.setAccountId(account.getAccountId());
            transactionOut.setType("WITHDRAW");
            transactionOut.setAccountId(account.getAccountId());
            transactionOut.setNotes(transaction.getNotes());
            return transaction;
        }
        throw new IllegalArgumentException("Insufficient Balance ");
    }

    public Page<Account> fetchAccountsGreaterThan(int pageNo, int pageSize,double balance) {
        final PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return this.accountsRepository.findByBalanceGreaterThan(balance, pageRequest);
    }
    public Page<Account> fetchAccountsBetween(int pageNo, int pageSize,double gtBalance, double ltBalance) {
        final PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return this.accountsRepository.findByBalanceBetween(gtBalance, ltBalance, pageRequest);
    }

    public Page<Account> fetchAllAccounts(int pageNo, int pageSize, String sortBy) {
        final PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<Account> pageResponse = this.accountsRepository.findAll(pageRequest);
        return pageResponse;
    }

    //we need to talk to Loans microservice using the post method and send the reponse back
    //@CircuitBreaker(name = "loanservice", fallbackMethod = "fallback")
    public Loan applyForLoan(long customerId, Loan loan) {
        //1st approach using Discovery client
        //return applyForLoanUsingDiscoveryClient(customerId, loan);
        //return applyForLoanUsingClientSideLoanBalancer(customerId, loan);
       // return applyForLoanUsingFeignClient(customerId, loan);

        //pushing the message to the kafka topic called orders-topic
        this.source.output().send(MessageBuilder.withPayload(loan).build());

        return fallback(null);
    }

    private Loan fallback(Exception exception){
        //log.error("Failed calling the service:: {}", exception.getMessage());
        return Loan.builder().loanId("1111").loanAmount(0).balanceAmount(0).status("TEMP_DOWN").build();
    }

    private Loan applyForLoanUsingFeignClient(long customerId, Loan loan) {
        final Loan loanResponse = feignClient.applyForLoan(customerId, loan).getBody();
        return loanResponse;
    }


    private Loan applyForLoanUsingDiscoveryClient(long customerId, Loan loan){
        final List<ServiceInstance> loanserviceInstances = this.discoveryClient.getInstances("LOANSERVICE");
        if (loanserviceInstances != null && !loanserviceInstances.isEmpty()){
            log.info("Number of instances of loan services: {}  ", loanserviceInstances.size());
            final ServiceInstance loanServiceInstance = loanserviceInstances.get(0);
            String loanServiceURI = loanServiceInstance.getUri().toString();
            String absoluteURL = loanServiceURI+"/api/customer/"+customerId+"/loans";
            System.out.println("Loan microservice URL :: "+ absoluteURL);
            final ResponseEntity<Loan> loanResponseEntity = this.restTemplate.postForEntity(absoluteURL, loan, Loan.class);

            //System.out.println("Loan Id :: " +loanResponseEntity.getBody().getLoanId());
           log.info("Status code after making the call :: {}",loanResponseEntity.getStatusCode().toString());
            return loanResponseEntity.getBody();
        }
        return null;
    }


    private Loan applyForLoanUsingClientSideLoanBalancer(long customerId, Loan loan) {
        final ResponseEntity<Loan> loanResponseEntity = this.restTemplate.postForEntity("http://LOANSERVICE/"+"/api/customer/"+customerId+"/loans", loan, Loan.class);
        //System.out.println("Loan Id :: " +loanResponseEntity.getBody().getLoanId());
        System.out.println("Status code after making the call :: "+loanResponseEntity.getStatusCode().toString());
        return loanResponseEntity.getBody();
    }
}