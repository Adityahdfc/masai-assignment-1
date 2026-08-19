package strategy;

public class PremiumCalculator {
    private PremiumStrategy premiumStrategy;

    public PremiumCalculator(PremiumStrategy premiumStrategy) {
        this.premiumStrategy = premiumStrategy;
    }

    public void setPremiumStrategy(PremiumStrategy premiumStrategy) {
        this.premiumStrategy = premiumStrategy;
    }

    public int getPremium(int basePremium) {
        return premiumStrategy.calculatePremium(basePremium);
    }
}
