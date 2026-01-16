import enums.SplitType;
import model.User;
import repository.InMemoryGroupRepository;
import service.BalanceSheetService;
import service.DebtSimplificationService;
import service.ExpenseService;
import service.GroupService;

import java.util.List;

public class SplitWiseMain {
    public static void main(String[] args) {
        //user
        User shubh = new User("u1", "Shubh");
        User bob = new User("u2", "Bob");
        User tom = new User("u3", "Tom");
        User jake = new User("u4", "Jake");

        InMemoryGroupRepository repo = new InMemoryGroupRepository();
        BalanceSheetService balanceSheetService = new BalanceSheetService();
        ExpenseService expenseService = new ExpenseService(balanceSheetService);
        DebtSimplificationService debtSimplificationService = new DebtSimplificationService();

        GroupService groupService = new GroupService(repo, expenseService, debtSimplificationService);

        /*----------------------------create groups----------------------*/

        String goaGroupId = groupService.createGroup("Goa Trip", List.of(shubh,bob,tom));
        String miscGroup = groupService.createGroup("Non-Group Expenses", List.of(shubh, bob, tom, jake));

        /*--------------add expense----------------------------*/

        groupService.addExpense(goaGroupId, "Lunch Day-1", 100, shubh, List.of(shubh,bob), SplitType.EQUAL, null);

        groupService.addExpense(goaGroupId, "Lunch Day-2", 100, bob, List.of(bob,tom), SplitType.EQUAL, null);

        /*-----------Simplify & print-------------*/
        groupService.simplifyDebts(goaGroupId);
        groupService.printBalances(goaGroupId);
    }
}
