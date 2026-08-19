package strategy;

public class EndowmentPremiumStrategy implements PremiumStrategy {

    @Override
    public int calculatePremium(int basePremium) {
        return (int) basePremium * 108 / 100;
    }
}
