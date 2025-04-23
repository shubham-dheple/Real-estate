# Real-estate

#Project Description:
The Real Estate Management System is a desktop-based application developed using Java Swing for the graphical user interface and JDBC for database connectivity. It connects to an Oracle Database to manage data related to properties, leases, clients, and transactions. This system aims to simplify the process of property management for real estate agencies or individual brokers.

Table for above project in database:

CREATE TABLE Tenant (
    Tenant_ID NUMBER PRIMARY KEY,
    Name VARCHAR2(100) NOT NULL,
    Contact_No VARCHAR2(15) UNIQUE,
    Email VARCHAR2(100) UNIQUE
);

CREATE TABLE Lease (
    Lease_ID NUMBER,
    Property_ID NUMBER,
    Tenant_ID NUMBER,
    Start_Date DATE,
    End_Date DATE
);

CREATE TABLE Payment (
    Payment_ID NUMBER ,
    Lease_ID NUMBER ,
    Amount DECIMAL(10,2) ,
    Payment_Date DATE
);

CREATE TABLE Property (
    Property_ID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Location VARCHAR2(100),
    Property_Type VARCHAR2(50),
    Property_Size VARCHAR2(50) 
);


