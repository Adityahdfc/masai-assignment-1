package model;

import constants.PolicyStatus;
import constants.PolicyType;
import constants.PolicyUrgency;

public abstract class Policy {
    private String policyId;
    private String policyHolderName;
    private int policyPremium;
    private PolicyStatus policyStatus;
    private PolicyUrgency policyUrgency;

    public String getPolicyId() {
        return policyId;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public double getPolicyPremium() {
        return policyPremium;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public PolicyUrgency getPolicyUrgency() {
        return policyUrgency;
    }

    public void setPolicyUrgency(PolicyUrgency policyUrgency) {
        this.policyUrgency = policyUrgency;
    }

    public Policy(String policyId, String policyHolderName, int policyPremium) {
        this.policyId = policyId;
        this.policyHolderName = policyHolderName;
        this.policyPremium = policyPremium;
        this.policyStatus = PolicyStatus.Pending;
        this.policyUrgency = PolicyUrgency.LOW;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyId='" + policyId + '\'' +
                ", policyHolderName='" + policyHolderName + '\'' +
                ", policyPremium=" + policyPremium +
                ", policyStatus=" + policyStatus +
                ", policyUrgency=" + policyUrgency +
                '}';
    }

    public abstract PolicyType policyType();
}
