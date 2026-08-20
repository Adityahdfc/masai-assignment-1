package store;

import exception.PolicyNotFoundException;
import model.Policy;

import java.util.*;

public class PolicyStore {
    private ArrayList<Policy> policyList;
    private HashSet<String> policySet;
    private HashMap<String, Policy> policyMap;
    private TreeMap<String, Policy> policyTreeMap;

    public PolicyStore() {

        policyList = new ArrayList<>();
        policySet = new HashSet<>();
        policyMap = new HashMap<>();

        policyTreeMap = new TreeMap<>(String::compareToIgnoreCase);


    }

    public void addPolicy(Policy policy) {
        policyList.add(policy);
        policySet.add(policy.getPolicyHolderName());
        policyMap.put(policy.getPolicyId(), policy);
        policyTreeMap.put(policy.getPolicyId(), policy);
    }

    public List<Policy> getAllPolicies() {
        return policyList;
    }

    public Set<String> getUniqueNames() {
        return policySet;
    }

    public Policy getPolicy(String policyId) throws PolicyNotFoundException {
        return policyMap.getOrDefault(policyId, null);
    }

    public TreeMap<String, Policy> getPoliciesByPolicyIdOrder() {
        return policyTreeMap;
    }
}