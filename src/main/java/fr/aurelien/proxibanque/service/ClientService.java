package fr.aurelien.proxibanque.service;

import fr.aurelien.proxibanque.model.*;
import fr.aurelien.proxibanque.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client client)
    {
        return clientRepository.save(client);
    }

    public List<Client> listClients()
    {
        return clientRepository.findAll();
    }

    public Optional<Client> findClientById(Long id)
    {
        return clientRepository.findById(id);
    }

    public List<Client> searchByName(String name)
    {
        return clientRepository.findClientsByName(name);
    }

    public Client updateClient(Long id, Client updatedClient)
    {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setLastName(updatedClient.getLastName());
                    client.setFirstName(updatedClient.getFirstName());
                    client.setAddress(updatedClient.getAddress());
                    client.setPostalCode(updatedClient.getPostalCode());
                    client.setCity(updatedClient.getCity());
                    client.setPhone(updatedClient.getPhone());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Clients not found"));
    }

    public void deleteClient(Long id)
    {
        clientRepository.deleteById(id);
    }

    public CheckingAccount createCheckingAccount(Long clientId, double initialBalance)
    {
        return clientRepository.findById(clientId)
                .map(client -> {
                    CheckingAccount account = new CheckingAccount(initialBalance);
                    client.setCheckingAccount(account);
                    Client savedClient = clientRepository.save(client);
                    return savedClient.getCheckingAccount();
                })
                .orElseThrow(() -> new RuntimeException("Client not found !"));
    }

    public SavingsAccount createSavingsAccount(Long clientId, double initialBalance)
    {
        return clientRepository.findById(clientId)
                .map(client -> {
                    SavingsAccount account = new SavingsAccount(initialBalance);
                    client.setSavingsAccount(account);
                    Client savedClient = clientRepository.save(client);
                    return savedClient.getSavingsAccount();
                })
                .orElseThrow(() -> new RuntimeException("Client not found !!"));
    }

    public List<Client> listWealthyClients()
    {
        return clientRepository.findClientsFortunes();
    }
}