package strategy;

import model.Split;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercentageSplitStrategy implements SplitStrategy{
    @Override
    public List<Split> split(double totalAmount, List<User> participants, Map<User, Double> metadata) {

        double totalPercentage = metadata.values().stream().mapToDouble(Double::doubleValue).sum();

        if(totalPercentage!=100.0)
            throw new IllegalArgumentException("Total percent should be 100");

        List<Split> splits = new ArrayList<>();

        for(User user : participants){
            splits.add(new Split(user, totalAmount*metadata.getOrDefault(user,0.0)/100));
        }

        return splits;
    }
}
