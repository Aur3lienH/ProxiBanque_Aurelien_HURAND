package fr.aurelien.proxibanque.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    private String city;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "checking_account_id")
    private CheckingAccount checkingAccount;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "savings_account_id")
    private SavingsAccount savingsAccount;

    public double getTotalBalance()
    {
        double total = 0;
        if (checkingAccount != null)
        {
            total += checkingAccount.getBalance();
        }
        if (savingsAccount != null)
        {
            total += savingsAccount.getBalance();
        }
        return total;
    }

    public boolean isWealthyClient()
    {
        return getTotalBalance() > 500000;
    }
}