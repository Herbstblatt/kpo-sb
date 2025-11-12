package hse.kpo.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private Long id;
    private String type;
    private Long bankAccountId;
    private BigDecimal amount;
    private String date;
    private String description;
    private Long categoryId;
}
