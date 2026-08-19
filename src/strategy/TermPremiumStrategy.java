package strategy;

public class TermPremiumStrategy implements PremiumStrategy {

    @Override
    public int calculatePremium(int basePremium) {
        return (int) basePremium * 100 / 100;
    }
}
