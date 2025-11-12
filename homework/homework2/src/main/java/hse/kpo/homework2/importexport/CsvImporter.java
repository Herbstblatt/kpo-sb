package hse.kpo.homework2.importexport;

import hse.kpo.homework2.dto.BankAccountDto;
import hse.kpo.homework2.dto.CategoryDto;
import hse.kpo.homework2.dto.ExportData;
import hse.kpo.homework2.dto.OperationDto;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter extends DataImporter {

    public CsvImporter(BankAccountFacade accountFacade, CategoryFacade categoryFacade,
                       OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);
    }

    @Override
    protected String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    @Override
    protected ExportData parseData(String content) {
        ExportData data = new ExportData();
        List<BankAccountDto> accounts = new ArrayList<>();
        List<CategoryDto> categories = new ArrayList<>();
        List<OperationDto> operations = new ArrayList<>();

        String[] sections = content.split("\n\n");
        
        for (String section : sections) {
            String[] lines = section.split("\n");
            if (lines.length == 0) continue;

            String header = lines[0].trim();
            
            if (header.equals("ACCOUNTS")) {
                for (int i = 2; i < lines.length; i++) {
                    String[] parts = lines[i].split(",");
                    if (parts.length >= 3) {
                        accounts.add(new BankAccountDto(
                                Long.parseLong(parts[0].trim()),
                                parts[1].trim(),
                                new BigDecimal(parts[2].trim())
                        ));
                    }
                }
            } else if (header.equals("CATEGORIES")) {
                for (int i = 2; i < lines.length; i++) {
                    String[] parts = lines[i].split(",");
                    if (parts.length >= 2) {
                        categories.add(new CategoryDto(
                                Long.parseLong(parts[0].trim()),
                                parts[1].trim()
                        ));
                    }
                }
            } else if (header.equals("OPERATIONS")) {
                for (int i = 2; i < lines.length; i++) {
                    String[] parts = lines[i].split(",");
                    if (parts.length >= 7) {
                        operations.add(new OperationDto(
                                Long.parseLong(parts[0].trim()),
                                parts[1].trim(),
                                Long.parseLong(parts[2].trim()),
                                new BigDecimal(parts[3].trim()),
                                parts[4].trim(),
                                parts[5].trim(),
                                Long.parseLong(parts[6].trim())
                        ));
                    }
                }
            }
        }

        data.setAccounts(accounts);
        data.setCategories(categories);
        data.setOperations(operations);

        return data;
    }
}
