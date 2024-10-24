package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.TransactionProxy;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.project_6_paymybuddy.Constant.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionProxy transactionProxy;
    @Mock
    private UserProxy userProxy;

    // Proceed a Successful Transaction
    @Test
    void proceedTransactionTest() {
        User sender = new User(); sender.setId(1); sender.setBalance(1024);
        User receiver = new User(); receiver.setId(16);

        when(userProxy.findUserWithId(16)).thenReturn(receiver);

        byte result = transactionService.proceedTransaction(sender, "16", "512", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_SUCCESS);
    }

    // Transaction Fail: receiverStr could not be converted to Int value
    @Test
    void proceedTransactionTest_failParseReceiverStr() {
        byte result = transactionService.proceedTransaction(new User(), "abc", "512", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_RECEIVER);
    }

    // Transaction Fail: amount could not be converted to Int value
    @Test
    void proceedTransactionTest_failParseAmount() {
        byte result = transactionService.proceedTransaction(new User(), "16", "abc", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_AMOUNT);
    }

    // Transaction Fail: Receiver couldn't be found from the Database
    @Test
    void proceedTransactionTest_failRetrieveReceiver() {
        when(userProxy.findUserWithId(16)).thenReturn(null);
        byte result = transactionService.proceedTransaction(new User(), "16", "512", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_RECEIVER);
    }

    // Transaction Fail: Amount outbounds (lower than minimum)
    @Test
    void proceedTransactionTest_failAmountLowerThanMin() {
        User receiver = new User(); receiver.setId(16);
        when(userProxy.findUserWithId(16)).thenReturn(receiver);
        byte result = transactionService.proceedTransaction(new User(), "16", "0", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_AMOUNT);
    }

    // Transaction Fail: Amount outbounds (Higher than maximum)
    @Test
    void proceedTransactionTest_failAmountHigherThanMax() {
        User receiver = new User(); receiver.setId(16);
        when(userProxy.findUserWithId(16)).thenReturn(receiver);
        byte result = transactionService.proceedTransaction(new User(), "16", "120000", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_AMOUNT);
    }

    // Transaction Fail: Label outbounds (char count too low)
    @Test
    void proceedTransactionTest_failLabelLowCharCount() {
        User receiver = new User(); receiver.setId(16);
        when(userProxy.findUserWithId(16)).thenReturn(receiver);
        byte result = transactionService.proceedTransaction(new User(), "16", "512", "a");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_LABEL);
    }

    // Transaction Fail: Label outbounds (char count too high)
    @Test
    void proceedTransactionTest_failLabelHighCharCount() {
        User receiver = new User(); receiver.setId(16);
        when(userProxy.findUserWithId(16)).thenReturn(receiver);
        byte result = transactionService.proceedTransaction(new User(), "16", "512", "this label is too long");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_LABEL);
    }

    // Transaction Fail: sender's inefficient balance
    @Test
    void proceedTransactionTest_failSenderBalance() {
        User sender = new User(); sender.setId(1); sender.setBalance(256);
        User receiver = new User(); receiver.setId(16);
        when(userProxy.findUserWithId(16)).thenReturn(receiver);
        byte result = transactionService.proceedTransaction(sender, "16", "512", "macbook");
        assertThat(result).isEqualTo(TRANSACTION_FAIL_BALANCE);
    }


    @Test
    void getTransactionSentFromUserIdTest() {
        // Prepare
        List<Transaction> transactions = new ArrayList<>();
        Transaction transactionA = new Transaction();
        transactionA.setAmount(512);
        transactionA.setReceiver(8);
        transactions.add(transactionA);

        // Arrange
        when(transactionProxy.findAllTransactionSentFromUserId(1)).thenReturn(transactions);
        when(userProxy.findUsernameWithId(anyInt())).thenReturn("John");

        // Verify
        List<Transaction> result = transactionService.getTransactionSentFromUserID(1);
        assertThat(result.getFirst().getAmount()).isEqualTo(512);
        assertThat(result.getFirst().getReceiverUsername()).isEqualTo("John");
    }

    // convert string to integer OK
    @Test
    void stringToIntegerTest_OK() {
        assertThat(transactionService.StringToInteger("8517")).isEqualTo(8517);
    }

    // convert string to integer Fail
    @Test
    void stringToIntegerTest_Fail() {
        assertThat(transactionService.StringToInteger("ABC123")).isNull();
    }

    // convert and round string to 2 decimals float OK
    @Test
    void StringToFloatTwoDecimals_OK() {
        assertThat(transactionService.StringToFloatTwoDecimals("53.593")).isEqualTo(53.59f);
    }

    // convert and round string to 2 decimals float Fail
    @Test
    void StringToFloatTwoDecimals_Fail() {
        assertThat(transactionService.StringToInteger("53_51")).isNull();
    }

    @Test
    void floatToStringAmountCurrencyTest_noDecimal() {
        assertThat(transactionService.floatToStringAmountCurrency(12.0f)).isEqualTo("12 €");
    }

    @Test
    void floatToStringAmountCurrencyTest_oneDecimal() {
        assertThat(transactionService.floatToStringAmountCurrency(12.3f)).isEqualTo("12.30 €");
    }

    @Test
    void floatToStringAmountCurrencyTest_twoDecimal() {
        assertThat(transactionService.floatToStringAmountCurrency(12.36f)).isEqualTo("12.36 €");
    }

    @Test
    void floatToStringAmountCurrencyTest_treeDecimalAndUp() {
        assertThat(transactionService.floatToStringAmountCurrency(12.36486f)).isEqualTo("12.36 €");
    }
}
