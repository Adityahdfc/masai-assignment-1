package strategy;

public class UlipPremiumStrategy implements PremiumStrategy {

    @Override
    public int calculatePremium(int basePremium) {
        return (int) basePremium * 112 / 100;
    }
}
