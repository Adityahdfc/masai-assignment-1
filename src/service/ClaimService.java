package service;

import config.AppConfig;
import constants.ClaimStatus;
import exception.InvalidClaimException;
import exception.PolicyNotFoundException;
import model.Claim;
import model.Policy;
import observer.ClaimEventPublisher;
import store.PolicyStore;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ClaimService {

    PriorityQueue<Claim> claimQueue;
    private PolicyStore store;
    private ClaimEventPublisher publisher;

    public ClaimService(PolicyStore store, ClaimEventPublisher publisher) {
        claimQueue = new PriorityQueue<>(Comparator.comparing(Claim::getStatus));
        this.store = store;
        this.publisher = publisher;
    }

    public Claim fileClaim(Claim claim) {

        // 1. Validate amount
        if (claim.getClaimAmount() <= 0 ||
                claim.getClaimAmount() > AppConfig.INSTANCE.maximumClaimAmount()) {

            throw new InvalidClaimException(
                    "Claim amount: " + claim.getClaimAmount() +
                            " must be > 0 and <= " + AppConfig.INSTANCE.maximumClaimAmount());
        }

        Policy policy = store.getPolicy(claim.getPolicyId());
        if (policy == null) {
            throw new PolicyNotFoundException("Policy not found: " + claim.getPolicyId());
        }

        try (AuditLogger logger = new AuditLogger()) {
            logger.log("Filed claim for policy: " + claim.getPolicyId() +
                    " | Amount: " + claim.getClaimAmount());
        }

        claimQueue.add(claim);
        return claim;
    }

    public void updateClaimStatus(Claim claim, ClaimStatus newStatus) {
        claim.updateStatus(newStatus);
        publisher.notifyAllObservers(claim);
    }

    public Claim getClaim() {
        return claimQueue.poll();
    }
}