package fr.aurelien.proxibanque.controller;

import fr.aurelien.proxibanque.model.Client;
import fr.aurelien.proxibanque.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> listClients()
    {
        return ResponseEntity.ok(clientService.listClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id)
    {
        return clientService.findClientById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client)
    {
        if (client.getId() != null)
        {
            return ResponseEntity.badRequest().build();
        }
        Client newClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
                                               @RequestBody Client client)
    {
        try
        {
            Client updatedClient = clientService.updateClient(id, client);
            return ResponseEntity.ok(updatedClient);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id)
    {
        try
        {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/checking-account")
    public ResponseEntity<Object> createCheckingAccount(@PathVariable Long id, @RequestParam double balance)
    {
        if (balance < 0)
        {
            return ResponseEntity.badRequest().body("Balance cannot be negative");
        }
        try
        {
            var account = clientService.createCheckingAccount(id, balance);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/savings-account")
    public ResponseEntity<Object> createSavingsAccount(@PathVariable Long id, @RequestParam double balance) {
        if (balance < 0)
        {
            return ResponseEntity.badRequest().body("Balance cannot be negative");
        }
        try
        {
            var account = clientService.createSavingsAccount(id, balance);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/wealthy")
    public ResponseEntity<List<Client>> listWealthyClients()
    {
        return ResponseEntity.ok(clientService.listWealthyClients());
    }
}