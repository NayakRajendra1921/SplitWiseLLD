package service;

import enums.SplitType;
import model.BalanceSheet;
import model.Group;
import model.User;
import repository.GroupRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupService {

    private final GroupRepository repo;
    private final ExpenseService expenseService;
    private final DebtSimplificationService debtSimplificationService;

    public GroupService(GroupRepository groupRepository, ExpenseService expenseService, DebtSimplificationService debtSimplificationService){
        this.repo = groupRepository;
        this.expenseService = expenseService;
        this.debtSimplificationService = debtSimplificationService;
    }


    public String createGroup(String name, List<User> members){
        String id = UUID.randomUUID().toString();
        Group group = new Group(id, name);
        members.forEach(group::addMember);

        repo.save(group);
        return id;
    }

    public void addMemeber(String groupId, User user){
        Group existingGroup = get(groupId);
        existingGroup.addMember(user);
        repo.save(existingGroup);
    }

    public void addExpense(String groupId, String description, double amount, User paidBy, List<User> participants, SplitType splitType, Map<User, Double> meta){
        expenseService.addExpense(get(groupId), description, amount, paidBy, participants, splitType, meta);
    }

    private Group get(String id){
        return repo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Group not found: "+ id));
    }

    public void simplifyDebts(String groupId){
        debtSimplificationService.simplifyDebts(get(groupId));
    }

    public void printBalances(String groupId){
        Group g = get(groupId);

        g.getMembers().forEach(u->{

            BalanceSheet sheet = g.getBalanceSheet(u);

            double owe =0, get=0;

            for(double v : sheet.getBalances().values()){
                if(v < 0)
                    owe+=v;
                else
                    get+=v;
            }

            System.out.printf(
                    "%s | Paid: %.2f | Expense: %.2f | You owe: %.2f | You get: %.2f%n",
                    u.getName(),
                    sheet.getTotalPaid(),
                    sheet.getTotalExpense(),
                    owe,
                    get
            );

            sheet.getBalances().forEach((other,val)->System.out.printf("%s %.2f %s%n", val>0 ?"<- get" : "->owe",Math.abs(val),other.getName()));

            System.out.println("-----------------------------------------");

        });
    }
}
