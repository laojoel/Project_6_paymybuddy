package org.example.project_6_paymybuddy.Services;

import jakarta.transaction.Transactional;
import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.TransactionProxy;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionProxy transactionProxy;
    @Autowired
    private UserProxy userProxy;

    @Transactional
    public byte proceedTransaction(User sender, String receiverStr, String amountStr, String label) {
        // Check if String are convertible to Int
        Integer receiverId = StringToInteger(receiverStr); if (receiverId==null) {return TRANSACTION_FAIL_RECEIVER;}
        Integer amount = StringToInteger(amountStr); if (amount==null) {return TRANSACTION_FAIL_AMOUNT;}

        User receiver = userProxy.findUserWithId(receiverId);
        if (receiver==null) {return TRANSACTION_FAIL_RECEIVER;}
        else if (amount < TRANSACTION_MIN_AMOUNT || amount > TRANSACTION_MAX_AMOUNT) {return TRANSACTION_FAIL_AMOUNT;}
        else if (label.length() < TRANSACTION_LABEL_MIN_LEN || label.length() > TRANSACTION_LABEL_MAX_LEN) {return TRANSACTION_FAIL_LABEL;}
        else if (sender.balance - amount < 0) {return TRANSACTION_FAIL_BALANCE;}
        else {
            userProxy.updateBalance(sender.id, sender.balance-=amount);
            userProxy.updateBalance(receiver.id, receiver.balance+=amount);
            transactionProxy.recordTransaction(sender.id, receiverId, Instant.now().toEpochMilli(), amount ,label);
            return TRANSACTION_SUCCESS;
        }
    }

    public Integer StringToInteger(String s) {
        Integer result = null; try {result = Integer.parseInt(s);} catch (NumberFormatException ignored){}
        return result;
    }

    public List<Transaction> getTransactionSentFromUserID(int senderId) {
        List<Transaction> transactions = transactionProxy.findAllTransactionSentFromUserId(senderId);
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        for (Transaction transaction : transactions) {
            transaction.setReceiverUsername(userProxy.findUsernameWithId(transaction.getReceiver()));
            transaction.setAmountString(transaction.getAmount() + " €");
        }
        return transactions;
    }
}
