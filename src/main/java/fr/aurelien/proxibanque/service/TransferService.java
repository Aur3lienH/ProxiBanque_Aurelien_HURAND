package fr.aurelien.proxibanque.service;

import fr.aurelien.proxibanque.model.BankAccount;
import fr.aurelien.proxibanque.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransferService {

    private final BankAccountRepository accountRepository;

    public boolean performTransfer(Long sourceId, Long destinationId, double amount)
    {
        if (amount <= 0)
        {
            log.error("Invalid amount: {}", amount);
            throw new IllegalArgumentException("Amount must be positive");
        }

        BankAccount source = accountRepository.findById(sourceId).orElseThrow(() -> new RuntimeException("Source account not found"));

        BankAccount destination = accountRepository.findById(destinationId).orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (source.equals(destination))
        {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (source.debit(amount))
        {
            destination.credit(amount);
            accountRepository.save(source);
            accountRepository.save(destination);

            return true;
        } else
        {
            throw new RuntimeException("Insufficient balance");
        }
    }

    public boolean performTransferByAccountNumber(String sourceAccountNumber, String destinationAccountNumber, double amount)
    {
        BankAccount source = accountRepository.findByAccountNumber(sourceAccountNumber).orElseThrow(() -> new RuntimeException("Source account not found"));

        BankAccount destination = accountRepository.findByAccountNumber(destinationAccountNumber).orElseThrow(() -> new RuntimeException("Destination account not found"));

        return performTransfer(source.getId(), destination.getId(), amount);
    }
}