package com.luv2code.pbl4.jobseekerapplication.utils;
public enum JobTypeEnum {

    FULLTIME("fulltime", "toàn thời gian"),
    PARTTIME("parttime", "bán thời gian");
    private final String displayName;
    private final String databaseName;

    JobTypeEnum(String displayName, String databaseName) {
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
        for (JobTypeEnum type : values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type.getDatabaseName();
            }
        }
        return null;
    }
    public static String getDisplayNameByDatabaseName(String databaseName) {
        for (JobTypeEnum type : values()) {
            if (type.getDatabaseName().equalsIgnoreCase(databaseName)) {
                return type.getDisplayName();
            }
        }
        return null;
    }
}
