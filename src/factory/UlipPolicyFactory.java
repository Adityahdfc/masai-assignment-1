package factory;

import model.Policy;
import model.UlipPolicy;

public class UlipPolicyFactory implements PolicyFactory {
    @Override
    public Policy getPolicy(String policyId, String PolicyHolderName, int basePremium) {
        return new UlipPolicy(policyId, PolicyHolderName, basePremium);
    }
}
