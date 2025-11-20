package fr.aurelien.proxibanque.repository;

import fr.aurelien.proxibanque.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    default List<Client> findClientsByName(String name) {
        return findAll().stream()
                .filter(client -> client.getLastName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Query("SELECT c FROM Client c WHERE " +
            "(c.checkingAccount.balance + COALESCE(c.checkingAccount.balance, 0)) > 500000")
    List<Client> findClientsFortunes();
}