package com.luv2code.pbl4.jobseekerapplication.utils;
public enum CareerLevelEnum {

    STAFF("staff", "Nhân viên"),
    MANAGER("manager", "Quản lý / Giám sát"),
    INTERN("intern", "Thực tập sinh");

    private final String displayName;
    private final String databaseName;

    CareerLevelEnum(String displayName, String databaseName) {
        this.displayName = displayName;
        this.databaseName = databaseName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDatabaseName() {
        return databaseName;
    }


    public static String getDatabaseNameByDisplayName(String displayName) {
        for (CareerLevelEnum type : values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type.getDatabaseName();
            }
        }
        return null;
    }
    public static String getDisplayNameByDatabaseName(String databaseName) {
        for (CareerLevelEnum type : values()) {
            if (type.getDatabaseName().equalsIgnoreCase(databaseName)) {
                return type.getDisplayName();
            }
        }
        return null;
    }
}
