package config;


public enum AppConfig {
    INSTANCE;

    public String companyName(){
        return "HDFC Life";
    }

    public int maximumClaimAmount(){
        return 500000;
    }
}
