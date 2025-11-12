package hse.kpo.homework2.facade;

import hse.kpo.homework2.factory.EntityFactory;
import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountFacade {
    private final BankAccountRepository repository;
    private final EntityFactory factory;

    public BankAccount createAccount(String name, BigDecimal balance) {
        BankAccount account = factory.createBankAccount(name, balance);
        return repository.save(account);
    }

    public BankAccount getAccount(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public BankAccount updateAccount(Long id, String name, BigDecimal balance) {
        BankAccount account = getAccount(id);
        if (name != null && !name.trim().isEmpty()) {
            account.setName(name);
        }
        if (balance != null && balance.compareTo(BigDecimal.ZERO) >= 0) {
            account.setBalance(balance);
        }
        return repository.save(account);
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.clear();
    }

    public BankAccount createAccountWithId(Long id, String name, BigDecimal balance) {
        BankAccount account = factory.createBankAccount(name, balance);
        account.setId(id);
        return repository.save(account);
    }
}