package model;

public class Split {
    private final User user;
    private double amount;

    public Split(User user){
        this.user=user;
    }

    public Split(User user, double amount){
        this.user=user;
        this.amount=amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    public double getAmount(){
        return amount;
    }

    public User getUser(){
        return user;
    }
}
