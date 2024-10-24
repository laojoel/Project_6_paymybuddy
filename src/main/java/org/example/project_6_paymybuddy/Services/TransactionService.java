package org.example.project_6_paymybuddy.Services;

import jakarta.transaction.Transactional;
import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.TransactionProxy;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;
import static org.example.project_6_paymybuddy.ApplicationStarter.logger;

@Service
public class TransactionService {

    @Autowired
    private TransactionProxy transactionProxy;
    @Autowired
    private UserProxy userProxy;

    @Transactional
    public byte proceedTransaction(User sender, String receiverStr, String amountStr, String label) {
        Integer receiverId = StringToInteger(receiverStr);
        if (receiverId==null) {
            logger.error("Transaction Fail: Receiver id [" + receiverStr + "] cannot be parsed");
            return TRANSACTION_FAIL_RECEIVER;
        }
        Float amount = StringToFloatTwoDecimals(amountStr);
        System.out.println("AmoutStr = " + amountStr + " | amountNum = " + amount);
        if (amount==null) {
            logger.error("Transaction Fail: amount [" + amountStr + "] cannot be parsed");
            return TRANSACTION_FAIL_AMOUNT;
        }

        User receiver = userProxy.findUserWithId(receiverId);
        if (receiver==null) {logger.error("Transaction Fail: Receiver id " + receiverId + " not found"); return TRANSACTION_FAIL_RECEIVER;}
        else if (amount < TRANSACTION_MIN_AMOUNT || amount > TRANSACTION_MAX_AMOUNT) {return TRANSACTION_FAIL_AMOUNT;}
        else if (label.length() < TRANSACTION_LABEL_MIN_LEN || label.length() > TRANSACTION_LABEL_MAX_LEN) {return TRANSACTION_FAIL_LABEL;}
        else if (sender.getBalance() - amount < 0.0f) {return TRANSACTION_FAIL_BALANCE;}
        else {
            userProxy.updateBalance(sender.getId(), sender.balance-=amount);
            userProxy.updateBalance(receiver.getId(), receiver.balance+=amount);
            transactionProxy.recordTransaction(sender.getId(), receiverId, Instant.now().toEpochMilli(), amount ,label);
            return TRANSACTION_SUCCESS;
        }
    }

    public Integer StringToInteger(String s) {
        Integer result = null; try {result = Integer.parseInt(s);} catch (NumberFormatException ignored){}
        return result;
    }
    public Float StringToFloatTwoDecimals(String s) {
        Float result = null; try {
            result = Float.parseFloat(s.replace(',', '.'));
            result = Math.round(result * 100) / 100f;
        } catch (NumberFormatException ignored){}
        return result;
    }
    public List<Transaction> getTransactionSentFromUserID(int senderId) {
        List<Transaction> transactions = transactionProxy.findAllTransactionSentFromUserId(senderId);
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        for (Transaction transaction : transactions) {
            transaction.setReceiverUsername(userProxy.findUsernameWithId(transaction.getReceiver()));
            transaction.setAmountString(floatToStringAmountCurrency(transaction.getAmount()));
        }
        return transactions;
    }

    public String floatToStringAmountCurrency(float f) {
        String s = String.valueOf(f);
        if (s.endsWith(".0")) {s = s.substring(0, s.length() - 2) + " €";} // no decimal (12.0 -> 12 €)
        else if(s.indexOf('.') == s.length()-2) {s = s + "0 €";} // 1 decimal (12.3 -> 12.30 €)
        else {s = s + " €";} // 2 decimals (12.35 -> 12.35 €)
        return s;
    }
}
