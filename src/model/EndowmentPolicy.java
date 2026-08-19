package model;

import constants.PolicyType;
import strategy.EndowmentPremiumStrategy;
import strategy.PremiumCalculator;


public class EndowmentPolicy extends Policy{

    private static PremiumCalculator premiumCalculator = new PremiumCalculator(new EndowmentPremiumStrategy());

    public EndowmentPolicy(String policyId, String policyHolderName, int basePremium) {
        super(policyId, policyHolderName, premiumCalculator.getPremium(basePremium));
    }

    @Override
    public PolicyType policyType(){
        return PolicyType.ENDOWMENT;
    }

}
