package hse.kpo.homework2.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hse.kpo.homework2.dto.BankAccountDto;
import hse.kpo.homework2.dto.CategoryDto;
import hse.kpo.homework2.dto.ExportData;
import hse.kpo.homework2.dto.OperationDto;
import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.model.Operation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonExportVisitor implements ExportVisitor {
    private final List<BankAccountDto> accounts = new ArrayList<>();
    private final List<CategoryDto> categories = new ArrayList<>();
    private final List<OperationDto> operations = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public JsonExportVisitor() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void visit(BankAccount account) {
        accounts.add(new BankAccountDto(
                account.getId(),
                account.getName(),
                account.getBalance()
        ));
    }

    @Override
    public void visit(Category category) {
        categories.add(new CategoryDto(
                category.getId(),
                category.getName()
        ));
    }

    @Override
    public void visit(Operation operation) {
        operations.add(new OperationDto(
                operation.getId(),
                operation.getType().name(),
                operation.getBankAccountId(),
                operation.getAmount(),
                operation.getDate().format(formatter),
                operation.getDescription(),
                operation.getCategoryId()
        ));
    }

    @Override
    public String getResult() {
        try {
            ExportData data = new ExportData(accounts, categories, operations);
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JSON", e);
        }
    }
}
