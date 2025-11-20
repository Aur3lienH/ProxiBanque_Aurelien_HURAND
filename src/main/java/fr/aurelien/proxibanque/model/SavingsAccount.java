package fr.aurelien.proxibanque.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("SAVINGS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SavingsAccount extends BankAccount
{

    @Column(name = "interest_rate")
    private double interestRate = 0.03;

    public SavingsAccount(double initialBalance)
    {
        super();
        setBalance(initialBalance);
        this.interestRate = 0.03;
    }

    public SavingsAccount(double initialBalance, double interestRate)
    {
        super();
        setBalance(initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public boolean debit(double amount)
    {
        if (amount <= 0)
        {
            return false;
        }

        if (getBalance() >= amount)
        {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }

    @Override
    public String getAccountType()
    {
        return "Savings Account";
    }

    public void applyInterest()
    {
        double interest = getBalance() * interestRate;
        credit(interest);
    }
}