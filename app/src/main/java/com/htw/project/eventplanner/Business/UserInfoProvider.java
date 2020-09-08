package com.htw.project.eventplanner.Business;

import com.htw.project.eventplanner.Model.User;

public class UserInfoProvider {

    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        UserInfoProvider.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

}
