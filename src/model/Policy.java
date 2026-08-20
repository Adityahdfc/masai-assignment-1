package model;

import constants.PolicyStatus;
import constants.PolicyType;

public abstract class Policy {
    private String policyId;
    private String policyHolderName;
    private int policyPremium;
    private PolicyStatus policyStatus;

    public String getPolicyId() {
        return policyId;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public int getPolicyPremium() {
        return policyPremium;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }


    public Policy(String policyId, String policyHolderName, int policyPremium) {
        this.policyId = policyId;
        this.policyHolderName = policyHolderName;
        this.policyPremium = policyPremium;
        this.policyStatus = PolicyStatus.Pending;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyId='" + policyId + '\'' +
                ", policyHolderName='" + policyHolderName + '\'' +
                ", policyPremium=" + policyPremium +
                ", policyStatus=" + policyStatus +
                '}';
    }

    public abstract PolicyType policyType();
}
