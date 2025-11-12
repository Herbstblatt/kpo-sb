package hse.kpo.homework2.visitor;

import hse.kpo.homework2.model.BankAccount;
import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.model.Operation;

public interface ExportVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
    String getResult();
}
