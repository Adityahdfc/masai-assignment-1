package constants;

public enum PolicyUrgency {
    HIGH(1), MEDIUM(2), LOW(3);

    private final int priority;

    PolicyUrgency(int priority) {
        this.priority = priority;
    }

     public int getPriority() {
        return priority;
    }
}
