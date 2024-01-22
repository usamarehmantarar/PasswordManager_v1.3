# PasswordManager_v1.3
Password Manager (GUI Enabled Desktop App) in Java, No Package.

#How to setup & use
1. Download the Folder "PasswordManager_v1.3" from the main repo on GitHub.
2. If it is in .zip format, extract it to a specific folder.
3. After extraction, open this code in any offline java IDE Studio.

#Database Setup

4. We used MySQL Database to store the data from the app. To set it up for your system, we recommend using XAMPP > Apache+MySQL (Status=Running)
5. Now, when apache server & MySQL Database is online, "Clean & Build" the Java App in IDE.
6. By Default, app uses MySQL Database "mysql" at url= (http://localhost/mysql)
7. So check that database naming "mysql" is present in your phpmyadmin. & All of the restrictions to the database connection string are disabled.
8. If you want to change the database, it can be done in the PasswordManager.java file. (But it is not recommneded, then you will have to manually setup some table settings.
9. By default, "mysql" database is already present in the MySQL Console. (If it will not be present, app will build it automatically.)

#User Authentication

10. App is not built to handle the online authentication of the users, due to hashing problems in the data transfers.
11. So it is recommended to use following credentials for the authentication:
username: admin
password: admin

App is using offline authentication of the users, at the time of login.

You can explore more functions of the app in the code.
P.S. We'll soon update the code and Readme file for more improvements.

Developers:
Asadullah Ranjha 
Bilal Kiyani
Usama Rehman
