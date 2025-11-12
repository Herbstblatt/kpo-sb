package hse.kpo.homework2.visitor;

import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.model.Operation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvExportVisitor implements ExportVisitor {
    private final List<String> accountLines = new ArrayList<>();
    private final List<String> categoryLines = new ArrayList<>();
    private final List<String> operationLines = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void visit(BankAccount account) {
        accountLines.add(String.format("%d,%s,%s",
                account.getId(),
                account.getName(),
                account.getBalance()
        ));
    }

    @Override
    public void visit(Category category) {
        categoryLines.add(String.format("%d,%s",
                category.getId(),
                category.getName()
        ));
    }

    @Override
    public void visit(Operation operation) {
        operationLines.add(String.format("%d,%s,%d,%s,%s,%s,%d",
                operation.getId(),
                operation.getType().name(),
                operation.getBankAccountId(),
                operation.getAmount(),
                operation.getDate().format(formatter),
                operation.getDescription() != null ? operation.getDescription() : "",
                operation.getCategoryId()
        ));
    }

    @Override
    public String getResult() {
        StringBuilder sb = new StringBuilder();

        sb.append("ACCOUNTS\n");
        sb.append("id,name,balance\n");
        accountLines.forEach(line -> sb.append(line).append("\n"));

        sb.append("\nCATEGORIES\n");
        sb.append("id,name\n");
        categoryLines.forEach(line -> sb.append(line).append("\n"));

        sb.append("\nOPERATIONS\n");
        sb.append("id,type,bankAccountId,amount,date,description,categoryId\n");
        operationLines.forEach(line -> sb.append(line).append("\n"));

        return sb.toString();
    }
}
