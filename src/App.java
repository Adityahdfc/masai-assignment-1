import config.AppConfig;
import constants.PolicyStatus;
import factory.EndowmentPolicyFactory;
import factory.PolicyFactory;
import factory.TermLifePolicyFactory;
import factory.UlipPolicyFactory;
import model.Policy;
import store.PolicyStore;

import java.util.ArrayList;
import java.util.List;


public class App {
    static void main() {

        System.out.println(AppConfig.INSTANCE.companyName());

        PolicyStore policyStore = new PolicyStore();

        PolicyFactory termLifePolicyFactory = new TermLifePolicyFactory();
        PolicyFactory endowmentPolicyFactory = new EndowmentPolicyFactory();
        PolicyFactory ulipPolicyFactory = new UlipPolicyFactory();

        Policy p1  = termLifePolicyFactory.getPolicy("HDFC-GET-1001","Anita Sharma",18500);
        Policy p2 = ulipPolicyFactory.getPolicy("HDFC-GET-1002","Rahul Mehta", 42000);
        Policy p3 = endowmentPolicyFactory.getPolicy("HDFC-GET-1003","Priya Nair", 27000);
        Policy p4 = termLifePolicyFactory.getPolicy("HDFC-GET-1004","Vikram Singh",15200);
        Policy p5 = ulipPolicyFactory.getPolicy("HDFC-GET-1005","Sneha Patel",36000);
        Policy p6 = endowmentPolicyFactory.getPolicy("HDFC-GET-1006", "Anita Sharma",22000);


        p1.setPolicyStatus(PolicyStatus.Active);

        p2.setPolicyStatus(PolicyStatus.Active);

        p3.setPolicyStatus(PolicyStatus.Lapsed);

        p4.setPolicyStatus(PolicyStatus.Active);

        p5.setPolicyStatus(PolicyStatus.Active);

        p6.setPolicyStatus(PolicyStatus.Pending);



        List<Policy> policies = new ArrayList<>(List.of(p1,p2,p3,p4,p5,p6));

        policies.stream().forEach(p -> policyStore.addPolicy(p));

        policyStore.printPolicy("HDFC-GET-1002");


    }
}