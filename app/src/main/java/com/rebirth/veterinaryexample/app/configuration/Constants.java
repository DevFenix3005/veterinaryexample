package com.rebirth.veterinaryexample.app.configuration;

public class Constants {

    static final String MEMBER_ROLE = "Member";
    static final String ADMIN_ROLE = "Admin";

    static final String[] ALL_ROLES = {MEMBER_ROLE, ADMIN_ROLE};

    private Constants() {
        throw new UnsupportedOperationException("You can't create an instance of this class");
    }
}
