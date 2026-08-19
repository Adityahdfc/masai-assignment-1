package observer;

import model.Claim;

import java.util.ArrayList;
import java.util.List;

public class ClaimEventPublisher {
    List<ClaimObserver> observers = new ArrayList<>();

    public void registerObserver(ClaimObserver claimObserver){
        observers.add(claimObserver);
    }

    public void removeObserver(ClaimObserver claimObserver){
        observers.remove(claimObserver);
    }

    public void notifyAllObservers(Claim claim){
        for(ClaimObserver observer : observers){
            observer.onClaimUpdate(claim);
        }
    }
}
