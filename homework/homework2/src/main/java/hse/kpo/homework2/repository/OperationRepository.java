package hse.kpo.homework2.repository;

import hse.kpo.homework2.model.Operation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OperationRepository {
    private final Map<Long, Operation> storage = new HashMap<>();
    private Long nextId = 1L;

    public Operation save(Operation operation) {
        if (operation.getId() == null) {
            operation.setId(nextId++);
        }
        storage.put(operation.getId(), operation);
        return operation;
    }

    public Optional<Operation> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Operation> findAll() {
        return List.copyOf(storage.values());
    }

    public List<Operation> findByBankAccountId(Long bankAccountId) {
        return storage.values().stream()
                .filter(op -> op.getBankAccountId().equals(bankAccountId))
                .collect(Collectors.toList());
    }

    public List<Operation> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return storage.values().stream()
                .filter(op -> !op.getDate().isBefore(start) && !op.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
        nextId = 1L;
    }
}
