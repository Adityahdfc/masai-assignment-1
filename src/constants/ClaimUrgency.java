package constants;


public enum ClaimUrgency {
    HIGH(1), MEDIUM(2), LOW(3);

    private final int priority;

    ClaimUrgency(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
