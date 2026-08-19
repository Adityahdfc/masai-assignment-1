package factory;

import model.Policy;
import model.TermLifePolicy;

public class TermLifePolicyFactory implements PolicyFactory{
    @Override
    public Policy getPolicy(String PolicyId, String PolicyHolderName, int basePremium) {
        return new TermLifePolicy(PolicyId, PolicyHolderName, basePremium);
    }
}
