package com.rebirth.veterinaryexample.app.configuration;

public class ConfigurationConstants {

    public static final String MEMBER_ROLE = "Member";
    public static final String VET_ROLE = "Veterinary";
    public static final String ADMIN_ROLE = "Admin";
    public static final String[] ALL_ROLES = {MEMBER_ROLE, VET_ROLE, ADMIN_ROLE};
    public static final String[] ADMINISTRATIVE_ROLES = {VET_ROLE, ADMIN_ROLE};

    private ConfigurationConstants() {
        throw new UnsupportedOperationException("You can't create an instance of this class");
    }
}
