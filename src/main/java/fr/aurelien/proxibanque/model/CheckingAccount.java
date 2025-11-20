package fr.aurelien.proxibanque.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CHECKING")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CheckingAccount extends BankAccount
{

    @Column(name = "overdraft_limit")
    private double overdraftLimit = 1000.0;

    public CheckingAccount(double initialBalance)
    {
        super();
        setBalance(initialBalance);
        this.overdraftLimit = 1000.0;
    }

    public CheckingAccount(double initialBalance, double overdraftLimit)
    {
        super();
        setBalance(initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean debit(double amount)
    {
        if (amount <= 0)
        {
            return false;
        }

        if (getBalance() - amount >= -overdraftLimit)
        {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }

    @Override
    public String getAccountType()
    {
        return "Checking Account";
    }

    public double getAvailableBalance()
    {
        return getBalance() + overdraftLimit;
    }
}