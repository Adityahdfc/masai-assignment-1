import config.AppConfig;
import constants.ClaimStatus;
import constants.ClaimUrgency;
import exception.InvalidClaimException;
import exception.PolicyNotFoundException;
import exception.UnknownPolicyTypeException;
import factory.PolicyFactory;
import model.Claim;
import model.Policy;
import observer.BranchLetterNotifier;
import observer.ClaimEventPublisher;
import observer.InAppNotifier;
import service.ClaimService;
import store.PolicyStore;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {

        System.out.println("=== HDFC LIFE POLICY CLAIMS CONSOLE ===\n");

        System.out.println("Company Name → " + AppConfig.INSTANCE.companyName() + "\n");

        // Seed data
        PolicyStore store = new PolicyStore();
        store.addPolicy(PolicyFactory.create("ENDOWMENT", "HDFC-LIFE-1003", "Priya Nair", 27000));
        store.addPolicy(PolicyFactory.create("TERM", "HDFC-LIFE-1004", "Vikram Singh", 15200));
        store.addPolicy(PolicyFactory.create("TERM", "HDFC-LIFE-1001", "Anita Sharma", 18500));
        store.addPolicy(PolicyFactory.create("ULIP", "HDFC-LIFE-1002", "Rahul Mehta", 42000));
        store.addPolicy(PolicyFactory.create("ULIP", "HDFC-LIFE-1005", "Sneha Patel", 36000));
        store.addPolicy(PolicyFactory.create("ENDOWMENT", "HDFC-LIFE-1006", "Anita Sharma", 22000));

        System.out.println("=== All Policies (Iterator) ===");
        Iterator<Policy> it = store.getAllPolicies().iterator();
        while (it.hasNext()) {
            Policy p = it.next();
            System.out.println(p.getPolicyId() + " | " + p.getPolicyHolderName());
        }
        System.out.println();

        System.out.println("Unique Customer Count → " + store.getUniqueNames().size());
        System.out.println("Lookup HDFC-LIFE-1004 → " + store.getPolicy("HDFC-LIFE-1004").getPolicyHolderName());
        System.out.println();

        System.out.println("=== TreeMap: Policies in Sorted Order ===");
        TreeMap<String, Policy> sorted = store.getPoliciesByPolicyIdOrder();
        for (Map.Entry<String, Policy> e : sorted.entrySet()) {
            System.out.println(e.getKey());
        }
        System.out.println();

        int ulipPremium = store.getPolicy("HDFC-LIFE-1002").getPolicyPremium();
        System.out.println("ULIP Premium for HDFC-LIFE-1002 → " + ulipPremium);
        System.out.println();

        // Observer setup
        ClaimEventPublisher publisher = new ClaimEventPublisher();
        publisher.registerObserver(new InAppNotifier());
        publisher.registerObserver(new BranchLetterNotifier());
        ClaimService claimService = new ClaimService(store, publisher);

        // Build claims
        Claim high = new Claim.Builder("HDFC-LIFE-1001", 25000, ClaimUrgency.HIGH)
                .hospitalName("Apollo")
                .remarks("Accident")
                .build();

        Claim medium = new Claim.Builder("HDFC-LIFE-1002", 15000, ClaimUrgency.MEDIUM).build();
        Claim low = new Claim.Builder("HDFC-LIFE-1004", 8000, ClaimUrgency.LOW).build();

        claimService.fileClaim(high);
        claimService.fileClaim(medium);
        claimService.fileClaim(low);

        System.out.println("=== Observer Notifications ===");
        System.out.println("Updating HIGH claim to APPROVED…");
        claimService.updateClaimStatus(high, ClaimStatus.ACCEPTED);
        System.out.println();

        System.out.println("=== PriorityQueue Poll Order (HIGH → MEDIUM → LOW) ===");
        System.out.println(claimService.getClaim());
        System.out.println(claimService.getClaim());
        System.out.println(claimService.getClaim());
        System.out.println();

        // Exceptions
        System.out.println("=== Exception Handling ===");
        try {
            store.getPolicy("HDFC-LIFE-9999");
        } catch (PolicyNotFoundException ex) {
            System.out.println("Caught: Policy not found → HDFC-LIFE-9999");
        }

        try {
            claimService.fileClaim(new Claim.Builder("HDFC-LIFE-1001", 600000, ClaimUrgency.HIGH).build());
        } catch (InvalidClaimException ex) {
            System.out.println("Caught: Invalid claim amount → 600000");
        }

        try {
            PolicyFactory.create("INVALID", "X", "Y", 1000);
        } catch (UnknownPolicyTypeException ex) {
            System.out.println("Caught: Unknown policy type → INVALID");
        }
        System.out.println();

        System.out.println("Audit log updated successfully.");
        System.out.println("\n=== END OF DEMO ===");
    }
}