package hse.kpo.homework2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportData {
    private List<BankAccountDto> accounts;
    private List<CategoryDto> categories;
    private List<OperationDto> operations;
}
