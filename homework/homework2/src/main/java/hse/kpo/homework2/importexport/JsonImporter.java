package hse.kpo.homework2.importexport;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.homework2.dto.ExportData;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonImporter extends DataImporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonImporter(BankAccountFacade accountFacade, CategoryFacade categoryFacade,
                        OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);
    }

    @Override
    protected String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    @Override
    protected ExportData parseData(String content) throws IOException {
        return objectMapper.readValue(content, ExportData.class);
    }
}
