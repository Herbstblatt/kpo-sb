package hse.kpo.homework2.facade;

import hse.kpo.homework2.factory.EntityFactory;
import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.model.Operation;
import hse.kpo.homework2.model.OperationType;
import hse.kpo.homework2.repository.BankAccountRepository;
import hse.kpo.homework2.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationFacade {
    private final OperationRepository operationRepository;
    private final BankAccountRepository accountRepository;
    private final EntityFactory factory;

    public Operation createOperation(OperationType type, Long bankAccountId, BigDecimal amount,
                                      LocalDateTime date, String description, Long categoryId) {
        Operation operation = factory.createOperation(type, bankAccountId, amount, date, description, categoryId);
        
        BankAccount account = accountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + bankAccountId));
        
        if (type == OperationType.INCOME) {
            account.addToBalance(amount);
        } else {
            account.subtractFromBalance(amount);
        }
        accountRepository.save(account);
        
        return operationRepository.save(operation);
    }

    public Operation getOperation(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Operation not found: " + id));
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public List<Operation> getOperationsByAccount(Long bankAccountId) {
        return operationRepository.findByBankAccountId(bankAccountId);
    }

    public void deleteOperation(Long id) {
        Operation operation = getOperation(id);
        
        BankAccount account = accountRepository.findById(operation.getBankAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        
        if (operation.getType() == OperationType.INCOME) {
            account.subtractFromBalance(operation.getAmount());
        } else {
            account.addToBalance(operation.getAmount());
        }
        accountRepository.save(account);
        
        operationRepository.deleteById(id);
    }

    public void deleteAll() {
        operationRepository.clear();
    }

    public Operation createOperationWithId(Long id, String type, Long bankAccountId, BigDecimal amount,
                                            LocalDateTime date, String description, Long categoryId) {
        OperationType operationType = OperationType.valueOf(type);
        Operation operation = factory.createOperation(operationType, bankAccountId, amount, date, description, categoryId);
        operation.setId(id);
        // При импорте не обновляем баланс, т.к. баланс уже импортируется из файла
        return operationRepository.save(operation);
    }
}
