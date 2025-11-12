package hse.kpo.homework2.repository;

import hse.kpo.homework2.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BankAccountRepository {
    private final Map<Long, BankAccount> storage = new HashMap<>();
    private Long nextId = 1L;

    public BankAccount save(BankAccount account) {
        if (account.getId() == null) {
            account.setId(nextId++);
        }
        storage.put(account.getId(), account);
        return account;
    }

    public Optional<BankAccount> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<BankAccount> findAll() {
        return List.copyOf(storage.values());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
        nextId = 1L;
    }
}
