package hse.kpo.homework2.importexport;

import hse.kpo.homework2.repository.BankAccountRepository;
import hse.kpo.homework2.repository.CategoryRepository;
import hse.kpo.homework2.repository.OperationRepository;
import hse.kpo.homework2.visitor.ExportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final BankAccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

    public void exportData(ExportVisitor visitor, String filePath) throws IOException {
        accountRepository.findAll().forEach(visitor::visit);
        categoryRepository.findAll().forEach(visitor::visit);
        operationRepository.findAll().forEach(visitor::visit);

        String result = visitor.getResult();
        Files.writeString(Paths.get(filePath), result);
        
        System.out.println("Данные экспортированы: " + filePath);
    }
}
