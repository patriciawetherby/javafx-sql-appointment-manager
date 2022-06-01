Title: C195PA
Description: This application allows users to login and access Customer and Appointment records that match the client_schedule database.
It allows them to add/update/delete records and gives alerts about appointment times if one is within 15 minutes. The business hours are
EST and the application conveniently converts the user's local time to match the business hours.

Date: 04/22/2022
Author: Patricia Wetherby
Contact: pcampo2@wgu.edu
Student Application Version v3
IntelliJ IDEA 2021.1.1.3
--------------------------
SDK Version:11 version 11.0.11
Java Version: 

IntelliJVersion:
IntelliJ IDEA 2021.1.1.3 (Community Edition)
Build #IC-211.7628.21, built on June 20, 2021

Runtime Version: 11.0.11+9-b1341.60 amd64
VM: OpenJDK 64-bit Server VM by JetBrains s.r.o

JavaSDK:
java-sdk-11.0.02

SQL:
my-sql-connector-java-8.0.27

JavaFX:
JavaFX API of version 17 by JavaFX 
Runtime Version 11.0.2

-------------------------

Directions:

In the login screen, the user can enter one of two credentials, UserID: test and Password: test or UserID: admin and Password: admin
A dialog box will pop up showing if there are any upcoming appointments in the next 15 minutes. The local time is automatically detected and the
times shown on the form are converted from UTC (from the database) to Local Time.
The user can select one of two tabs to modify the customer or appointment data. A customer cannot be deleted if there are any appointment records for that customer.
When creating an appointment, the available times are automatically converted from EST Business Hours (8:00am EST - 10:00pm EST) to Local Time 24-Hour format.
The local time will show up as 12 hour format in the table view. This allows the user to only set appointments during business hours.

To exit the application, one can click the exit buttons in the login screen or in the main menu.
