package hse.kpo.homework2.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Operation {
    private Long id;
    private OperationType type;
    private Long bankAccountId;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;
    private Long categoryId;

    public Operation(Long id, OperationType type, Long bankAccountId, BigDecimal amount,
                     LocalDateTime date, String description, Long categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }
}
