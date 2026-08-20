package model;

import constants.ClaimStatus;
import constants.ClaimUrgency;

public class Claim {
    private String policyId;
    private double claimAmount;
    private ClaimUrgency urgency;
    private String hostpitalName;
    private String remarks;
    private ClaimStatus status;

    public String getPolicyId() {
        return policyId;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public ClaimUrgency getUrgency() {
        return urgency;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getHostpitalName() {
        return hostpitalName;
    }

    public ClaimStatus getStatus() {
        return status;
    }

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

    @Override
    public String toString() {
        return "Claim{" +
                "policyId='" + policyId + '\'' +
                ", claimAmount=" + claimAmount +
                ", urgency=" + urgency +
                ", hospitalName='" + hostpitalName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                '}';
    }


    public static class Builder {
        private String policyId;
        private double claimAmount;
        private ClaimUrgency urgency;
        private String hostpitalName = null;
        private String remarks = null;
        private ClaimStatus status = ClaimStatus.SUBMITTED;

        public Builder(String policyId, double claimAmount, ClaimUrgency urgency) {
            this.policyId = policyId;
            this.claimAmount = claimAmount;
            this.urgency = urgency;
        }

        public Builder hospitalName(String name) {
            this.hostpitalName = name;
            return this;
        }

        public Builder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        public Claim build() {
            return new Claim(this);
        }


    }
}

