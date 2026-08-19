package factory;

import model.Policy;


public interface PolicyFactory {
    public Policy getPolicy( String policyId, String policyHolderName,int basePremium);
}
