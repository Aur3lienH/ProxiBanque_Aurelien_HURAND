package fr.aurelien.proxibanque.controller;

import fr.aurelien.proxibanque.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<?> performTransfer(@RequestBody Map<String, Object> request)
    {
        try
        {
            Long sourceId = Long.valueOf(request.get("sourceId").toString());
            Long destinationId = Long.valueOf(request.get("destinationId").toString());
            double amount = Double.parseDouble(request.get("amount").toString());

            transferService.performTransfer(sourceId, destinationId, amount);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Transfer completed successfully ! Bravo !"
            ));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/by-account-number")
    public ResponseEntity<?> performTransferByAccountNumber(@RequestBody Map<String, Object> request)
    {
        try
        {
            String sourceAccountNumber = request.get("sourceAccountNumber").toString();
            String destinationAccountNumber = request.get("destinationAccountNumber").toString();
            double amount = Double.parseDouble(request.get("amount").toString());

            transferService.performTransferByAccountNumber(sourceAccountNumber, destinationAccountNumber, amount);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Transfer completed successfully"
            ));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}