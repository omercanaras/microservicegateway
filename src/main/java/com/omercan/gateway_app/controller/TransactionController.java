package com.omercan.gateway_app.controller;

import com.google.gson.JsonElement;
import com.omercan.gateway_app.model.service.AbstractTransactionService;
import com.omercan.gateway_app.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("gateway/transaction")
@RestController
public class TransactionController
{
    @Autowired
    private AbstractTransactionService transactionService;

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer transactionId)
    {
        JsonElement transaction = transactionService.findById(transactionId);

        if(transaction != null)
        {
            transactionService.deleteById(transactionId);
            return new ResponseEntity<>("Transaction (transaction ID: " + transactionId + ") has been deleted.", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Transaction (transaction ID: " + transactionId + ") is not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<JsonElement> findById(@PathVariable Integer transactionId)
    {
        JsonElement transaction = transactionService.findById(transactionId);

        return transaction != null ? new ResponseEntity<>(transaction, HttpStatus.FOUND)
                                    : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JsonElement> save(@RequestBody JsonElement transaction)
    {
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @GetMapping
    public ResponseEntity<List<JsonElement>> getAllTransactionsOfAuthorizedUser(@AuthenticationPrincipal UserPrincipal user)
    {
        return ResponseEntity.ok(transactionService.findAllByUserID(user.getId()));
    }
}
