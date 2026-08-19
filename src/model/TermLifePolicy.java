package model;

import constants.PolicyType;
import strategy.PremiumCalculator;
import strategy.TermPremiumStrategy;

public class TermLifePolicy extends Policy{
    private static PremiumCalculator premiumCalculator = new PremiumCalculator(new TermPremiumStrategy());
    public TermLifePolicy(String policyId, String policyHolderName, int basePremium) {
        super(policyId, policyHolderName, premiumCalculator.getPremium(basePremium));
    }

    @Override
    public PolicyType policyType() {
        return PolicyType.TERM;
    }

}
