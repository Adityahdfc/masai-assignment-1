package model;

import constants.ClaimStatus;
import constants.ClaimUrgency;
import constants.PolicyStatus;
import constants.PolicyUrgency;

public class Claim {
    private String policyId;
    private double claimAmount;
    private ClaimUrgency urgency;
    private String hostpitalName;
    private String remarks;
    private ClaimStatus status;

    private Claim(Builder builder) {
        this.policyId = builder.policyId;
        this.claimAmount = builder.claimAmount;
        this.urgency = builder.urgency;
        this.hostpitalName = builder.hostpitalName;
        this.remarks = builder.remarks;
        this.status = builder.status;
    }

    public void updateStatus(ClaimStatus newStatus) {
        this.status = newStatus;
    }

    public static class Builder
    {
        private String policyId;
        private double claimAmount;
        private ClaimUrgency urgency;
        private String hostpitalName = null;
        private String remarks = null;
        private ClaimStatus status = ClaimStatus.SUBMITTED;

        public Builder(String policyId,double claimAmount, ClaimUrgency urgency){
            this.policyId = policyId;
            this.claimAmount = claimAmount;
            this.urgency = urgency;
        }

        public Builder hospitalName(String name){
            this.hostpitalName = name;
            return this;
        }

        public Builder remarks(String remarks){
            this.remarks = remarks;
            return this;
        }

        public Claim build(){
            return new Claim(this);
        }


    }
}

