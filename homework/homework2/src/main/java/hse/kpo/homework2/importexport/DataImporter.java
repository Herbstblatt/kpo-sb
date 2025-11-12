package hse.kpo.homework2.importexport;

import hse.kpo.homework2.dto.ExportData;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public abstract class DataImporter {
    protected final BankAccountFacade accountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    public final void importData(String filePath) throws IOException {
        String content = readFile(filePath);
        ExportData data = parseData(content);
        validateData(data);
        clearData();
        importParsedData(data);
    }

    protected abstract String readFile(String filePath) throws IOException;

    protected abstract ExportData parseData(String content) throws IOException;

    private void validateData(ExportData data) {
        if (data == null) {
            throw new IllegalArgumentException("Parsed data is null");
        }
    }

    private void clearData() {
        // Используем фасады для очистки данных
        operationFacade.deleteAll();
        accountFacade.deleteAll();
        categoryFacade.deleteAll();
    }

    private void importParsedData(ExportData data) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        if (data.getAccounts() != null) {
            data.getAccounts().forEach(dto -> {
                accountFacade.createAccountWithId(dto.getId(), dto.getName(), dto.getBalance());
            });
        }

        if (data.getCategories() != null) {
            data.getCategories().forEach(dto -> {
                categoryFacade.createCategoryWithId(dto.getId(), dto.getName());
            });
        }

        if (data.getOperations() != null) {
            data.getOperations().forEach(dto -> {
                operationFacade.createOperationWithId(
                        dto.getId(),
                        dto.getType(),
                        dto.getBankAccountId(),
                        dto.getAmount(),
                        LocalDateTime.parse(dto.getDate(), formatter),
                        dto.getDescription(),
                        dto.getCategoryId()
                );
            });
        }
    }
}
