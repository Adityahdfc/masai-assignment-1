package observer;

import model.Claim;

public class InAppNotifier implements ClaimObserver{

    @Override
    public void onClaimUpdate(Claim claim){
        System.out.println("In App Notification");
        System.out.println(claim);
    }

}
