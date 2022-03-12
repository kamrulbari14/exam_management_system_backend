package com.exam.management.exammanagementsystem.util;

public final class UrlConstraint {
    private UrlConstraint() {
    }

    public static class AuthManagement {
        public static final String ROOT = "/auth";
        public static final String LOGIN = "/login";
    }

    public static class UserManagement {
        public static final String ROOT = "/user";
    }

    public static class RoleManagement {
        public static final String SAVE_ROLE = "/saveRole";
        public static final String GET_ROLES = "/getRoles";
    }

    public static class UnAuthorizedEndPoint {
        public static final String RESET_PASSWORD = "/reset-password";
    }

}