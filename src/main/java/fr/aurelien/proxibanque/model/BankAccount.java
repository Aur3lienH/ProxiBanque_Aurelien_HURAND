package fr.aurelien.proxibanque.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BankAccount
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private double balance;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @PrePersist
    protected void onCreate()
    {
        if (openingDate == null)
        {
            openingDate = LocalDate.now();
        }
        if (accountNumber == null)
        {
            accountNumber = generateAccountNumber();
        }
    }

    private String generateAccountNumber()
    {
        return "FR76" + System.currentTimeMillis();
    }

    public abstract boolean debit(double amount);
    public abstract String getAccountType();

    public void credit(double amount)
    {
        if (amount > 0)
        {
            this.balance += amount;
        }
    }
}