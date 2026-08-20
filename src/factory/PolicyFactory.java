package factory;

import exception.UnknownPolicyTypeException;
import model.EndowmentPolicy;
import model.Policy;
import model.TermLifePolicy;
import model.UlipPolicy;


public class PolicyFactory {
    public static Policy create(String type, String policyId, String policyHolderName, int basePremium) {
        return switch (type.toUpperCase()) {
            case "TERM" -> new TermLifePolicy(policyId, policyHolderName, basePremium);
            case "ENDOWMENT" -> new EndowmentPolicy(policyId, policyHolderName, basePremium);
            case "ULIP" -> new UlipPolicy(policyId, policyHolderName, basePremium);
            default -> throw new UnknownPolicyTypeException(type + "Unknown policy type");
        };
    }
}
