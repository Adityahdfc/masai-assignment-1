package observer;

import model.Claim;

public interface ClaimObserver {
    public void onClaimUpdate(Claim claim);

}
