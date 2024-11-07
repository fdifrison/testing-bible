package com.fdifrison.util;

import com.fdifrison.app.User;
import org.assertj.core.api.AbstractAssert;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    // TODO custom assertionClass to use assertj fluent api in asserting custom objects

    protected UserAssert(User user) {
        super(user, UserAssert.class);
    }

    public static UserAssert assertThat(User actual) {
        return new UserAssert(actual);
    }

    public UserAssert hasUsername(String username) {
        isNotNull();
        if (!actual.getUsername().contains(username)) {
            failWithMessage("Expected user to have username %s", username);
        }
        return this;
    }
}
