package hse.kpo.homework2.factory;

import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.model.Operation;
import hse.kpo.homework2.model.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class EntityFactory {

    public BankAccount createBankAccount(String name, BigDecimal balance) {
        validateBankAccount(name, balance);
        return new BankAccount(null, name, balance);
    }

    public Category createCategory(String name) {
        validateCategory(name);
        return new Category(null, name);
    }

    public Operation createOperation(OperationType type, Long bankAccountId, BigDecimal amount,
                                      LocalDateTime date, String description, Long categoryId) {
        validateOperation(type, bankAccountId, amount, date, categoryId);
        return new Operation(null, type, bankAccountId, amount, date, description, categoryId);
    }

    private void validateBankAccount(String name, BigDecimal balance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank account name cannot be empty");
        }
        if (balance == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }

    private void validateCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
    }

    private void validateOperation(OperationType type, Long bankAccountId, BigDecimal amount,
                                    LocalDateTime date, Long categoryId) {
        if (type == null) {
            throw new IllegalArgumentException("Operation type cannot be null");
        }
        if (bankAccountId == null) {
            throw new IllegalArgumentException("Bank account ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
    }
}
