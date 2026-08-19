package model;

import constants.PolicyType;
import strategy.PremiumCalculator;
import strategy.UlipPremiumStrategy;

public class UlipPolicy extends Policy {
    private static PremiumCalculator premiumCalculator = new PremiumCalculator(new UlipPremiumStrategy());

    public UlipPolicy(String policyId, String policyHolderName, int basePremium) {
        super(policyId, policyHolderName, premiumCalculator.getPremium(basePremium));
    }

    public PolicyType policyType() {
        return PolicyType.ULIP;
    }

}
