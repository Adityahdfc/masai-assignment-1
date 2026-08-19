package factory;

import model.EndowmentPolicy;
import model.Policy;

public class EndowmentPolicyFactory implements PolicyFactory {
    @Override
    public Policy getPolicy(String policyId, String PolicyHolderName, int basePremium){
        return new EndowmentPolicy(policyId, PolicyHolderName, basePremium);
    }
}
