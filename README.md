# Password Manager v1.3 Overview

## Setup & Usage Instructions:

1. **Download:**
   - Download the "PasswordManager_v1.3" folder from the main GitHub repository.

2. **Extraction:**
   - If the download is in .zip format, extract it to a designated folder.

3. **Open in Java IDE:**
   - After extraction, open the code in any offline Java IDE Studio.

## Database Setup:

4. **MySQL Database:**
   - The app utilizes MySQL Database to store data.
   - Recommended Setup: XAMPP > Apache+MySQL (Status=Running)

5. **Build the App:**
   - Ensure Apache server & MySQL Database are online.
   - "Clean & Build" the Java App in the IDE.

6. **Default Database:**
   - By default, the app uses the "mysql" database at url= (http://localhost:3306/mysql).
   - Verify the existence of the "mysql" database in phpMyAdmin with all restrictions disabled.

7. **Database Customization:**
   - If you want to change the database, modify the PasswordManager.java file.
   - Changing the database is not recommended unless you manually set up required table settings.

8. **Automatic Database Creation:**
   - The app automatically builds the "mysql" database if not present in the MySQL Console.

## User Authentication:

10. **Offline Authentication:**
    - The app does not handle online authentication due to hashing issues in data transfers.
    - Recommended Credentials:
      - Username: admin
      - Password: admin
    - Offline authentication occurs at the time of login.

## Password Manager Functions Overview:

### 1. Add Password
- **Description:** Add a new password entry.
- **Input Parameters:**
  - Website: Name of the associated website.
  - Username: Account name for the website.
  - Password: Password for the specified account.
  - Google Account: Additional information, e.g., Google account details.

### 2. Search Password
- **Description:** Search for a password entry based on the website name.
- **Input Parameters:**
  - Website Name: Name of the website for password retrieval.

### 3. Refresh Table
- **Description:** Update the display table with the latest data from the Password Manager.
- **Functionality:** Ensures immediate reflection of changes or additions in the user interface.

### 4. Delete Password
- **Description:** Delete a specific password entry from the Password Manager.
- **Input Parameters:**
  - Website Name: Name of the website associated with the password to be deleted.

Explore additional functions in the code. Expect updates for more improvements.

**Original Authors:**
- Asadullah Ranjha
- Bilal Kiyani
- Usama Rehman
