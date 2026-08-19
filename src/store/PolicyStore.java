package store;

import model.Policy;

import java.util.*;
import java.util.stream.Collectors;

public class PolicyStore {
    private ArrayList<Policy> policyList;
    private HashSet<Policy> policySet;
    private HashMap<String, Policy> policyMap;
    private TreeMap<String, Policy> policyTreeMap;
    private PriorityQueue<Policy> policyPriorityQueue;

    public PolicyStore() {

        policyList = new ArrayList<>();
        policySet = new HashSet<>();
        policyMap = new HashMap<>();

        policyTreeMap = new TreeMap<>(String::compareToIgnoreCase);

        policyPriorityQueue = new PriorityQueue<>(Comparator.comparingInt((p -> p.getPolicyUrgency().getPriority())));

    }

    public void addPolicy(Policy policy) {
        policyList.add(policy);
        policySet.add(policy);
        policyMap.put(policy.getPolicyId(), policy);
        policyTreeMap.put(policy.getPolicyId(), policy);
        policyPriorityQueue.add(policy);
    }

    public void printAllPolicies() {
        Iterator<Policy> itr = policyList.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public void printUniqueNames() {
        List<String> uniqueNames = policyList.stream().map(Policy::getPolicyHolderName).collect(Collectors.toCollection(ArrayList::new));
        Iterator<String> itr = uniqueNames.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public void printPolicy(String policyId) {
        System.out.println(policyMap.get(policyId));
    }

    public void printPoliciesByPolicyIdOrder() {
        Iterator<Map.Entry<String, Policy>> itr = policyTreeMap.entrySet().iterator();

        while (itr.hasNext()) {
            System.out.println(itr.next().getValue());
        }

    }

    public void printPoliciesByPolicyUrgencyOrder() {
        PriorityQueue<Policy> temp = new PriorityQueue<>(policyPriorityQueue);

        List<Policy> orderedPolicies = new ArrayList<>();

        while (!temp.isEmpty()) {
            orderedPolicies.add(temp.poll());
        }

        Iterator<Policy> itr = orderedPolicies.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}