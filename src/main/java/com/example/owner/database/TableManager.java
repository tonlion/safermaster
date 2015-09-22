package com.example.owner.database;

/**
 * Created by Owner on 2015/9/22.
 */
public class TableManager {
    public static class SMSTable {
        public static final String TABLE_NAME = "sms";
        public static final String COL_PHONE_NUMBER = "phoneNumber";
        public static final String COL_DATE = "date";
        public static final String COL_MESSAGE = "message";

        public static String createTable() {
            StringBuilder builder = new StringBuilder();
            builder.append("create table if not exists ");
            builder.append(TABLE_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBER);
            builder.append(" varchar(20) not null,");
            builder.append(COL_MESSAGE);
            builder.append(" varchar(1000) null,");
            builder.append(COL_DATE);
            builder.append(" varchar(20) null");
            builder.append(")");
            return builder.toString();
        }
    }

    public static class BlackListTable {
        public static final String TABLE_NAME = "blacklist";
        public static final String COL_PHONE_NUMBER = "phoneNumber";
        public static final String COL_COME_NAME = "name";

        public static String createTable() {
            StringBuilder builder = new StringBuilder();
            builder.append("create table if not exists ");
            builder.append(TABLE_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBER);
            builder.append(" varchar(20) not null primary key,");
            builder.append(COL_COME_NAME);
            builder.append(" varchar(20) null");
            builder.append(")");
            return builder.toString();
        }
    }

    public static class PhoneTable {
        public static final String TABLE_NAME = "phone";
        public static final String COL_PHONE_NUMBER = "phoneNumber";
        public static final String COL_COME_NAME = "name";
        public static final String COL_DATE = "date";
        public static final String COL_ATTRIBUTION = "attribution";

        public static String createTable() {
            StringBuilder builder = new StringBuilder();
            builder.append("create table if not exists ");
            builder.append(TABLE_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBER);
            builder.append(" varchar(20) not null,");
            builder.append(COL_COME_NAME);
            builder.append(" varchar(20) null,");
            builder.append(COL_DATE);
            builder.append(" varchar(20) null,");
            builder.append(COL_ATTRIBUTION);
            builder.append(" varchar(20) null");
            builder.append(")");
            return builder.toString();
        }
    }
}
