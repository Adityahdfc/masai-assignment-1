package observer;

import model.Claim;

public class BranchLetterNotifier implements ClaimObserver {

    @Override
    public void onClaimUpdate(Claim claim){
        System.out.println("Branch Letter Notification");
        System.out.println(claim);
    }

}
