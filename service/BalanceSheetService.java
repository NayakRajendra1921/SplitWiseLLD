package service;

import model.Group;
import model.Split;
import model.User;

import java.util.List;

public class BalanceSheetService {

    public void updateBalances(Group group, User paidBy, List<Split> splits){
        double totalAmount = splits.stream().mapToDouble(Split::getAmount).sum();
        group.getBalanceSheet(paidBy).addTotalPaid(totalAmount);

        for(Split split : splits){
            User user = split.getUser();
            double amount = split.getAmount();
            group.getBalanceSheet(user).addTotalExpense(amount);

            if(!user.equals(paidBy)){
                group.getBalanceSheet(user).addBalance(paidBy,-amount);
                group.getBalanceSheet(paidBy).addBalance(user,amount);
            }
        }
    }
}
