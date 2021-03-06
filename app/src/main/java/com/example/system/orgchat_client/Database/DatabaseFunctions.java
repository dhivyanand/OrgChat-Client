package com.example.system.orgchat_client.Database;

public class DatabaseFunctions {

    public static String createUserTable(){

        return "CREATE TABLE USER (NAME VARCHAR, DOB VARCHAR, PROFILE VARCHAR, PHONE VARCHAR, ADDRESS VARCHAR)";
    }

    public static String createDepartmentTable(){

        return "CREATE TABLE DEPARTMENT (DEPARTMENT_ID VARCHAR,DEPARTMENT VARCHAR)";
    }

    public static String createSubDepartmentTable(){

        return "CREATE TABLE SUBDEPARTMENT (SUBDEPARTMENT_ID VARCHAR,SUBDEPARTMENT VARCHAR,DEPARTMENT VARCHAR)";
    }

    public static String createMessageTable(){

        return "CREATE TABLE MESSAGE (MESSAGE_ID VARCHAR , SENDER_ID VARCHAR , TITLE VARCHAR , MESSAGE VARCHAR , MESSAGE_TYPE VARCHAR , DATE VARCHAR , TIME VARCHAR) ";
    }

    public static String createAttachmentTable(){

        return "CREATE TABLE ATTACHMENT (ATTACHMENT_ID VARCHAR , MAP_ID VARCHAR , THUMBNAIL VARCHAR , FILE_LOCATION VARCHAR)";
    }

    public static String createCircularTable(){

        return "CREATE TABLE CIRCULAR (CIRCULAR_ID VARCHAR PRIMARY KEY, TITLE VARCHAR , MESSAGE VARCHAR , TIME VARCHAR , RW VARCHAR)";
    }

    public static String createFileTable(){

        return "CREATE TABLE FILE (MESSAGE_ID VARCHAR , NAME VARCHAR , LOCATION VARCHAR)";
    }

    public static String createDateTable(){

        return "CREATE TABLE DATE (TYPE VARCHAR , LAST_UPDATE VARCHAR , COUNT INT)";
    }

}
