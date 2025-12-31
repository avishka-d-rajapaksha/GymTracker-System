# 🏋️ GymTracker System
> **HNDIT 3012 - Object Oriented Programming Assignment (Group 10)**
> A comprehensive JavaFX-based management system for modern fitness centers.

## 📖 Project Overview
GymTracker is a digital solution designed to replace paper-based records. It allows administrators to manage memberships and revenue while providing instructors with an efficient way to track daily attendance and membership validity.

**Key Technical Implementations:**
* **Security:** SHA-256 Hashing for passwords via `SecurityUtils`.
* **Persistence:** Binary Serialization (`.bin`) for high-speed attendance logs and Text (`.txt`) for member data.
* **Architecture:** Multi-layered MVC (Model-View-Controller) Design Pattern.
* **Automation:** Automated Beautiful HTML Email Alerts via SMTP (Jakarta Mail).

---

## 📸 System Screenshots
Here is a visual tour of the application:

### 🔐 Login & Security
![Login Screen](GymTracker/Screenshot%202025-12-22%20225459.png)
![Security Verification](GymTracker/Screenshot%202025-12-22%20225516.png)

### 📊 Dashboards (Admin & Instructor)
![Admin Dashboard](GymTracker/Screenshot%202025-12-22%20225524.png)
![Instructor Dashboard](GymTracker/Screenshot%202025-12-22%20225558.png)

### 👥 Member Management
![Add Member](GymTracker/Screenshot%202025-12-22%20225645.png)
![Member List](GymTracker/Screenshot%202025-12-22%20225815.png)

### 📈 Reports & Analytics
![Attendance Logs](GymTracker/Screenshot%202025-12-22%20230128.png)
![Financial Reports](GymTracker/Screenshot%202025-12-22%20230135.png)

---

## 🚀 Key Features
### 1. Secured Access Control
* **Role-Based Access:** Admins have full financial control; Instructors are restricted to attendance marking.
* **Encryption:** All passwords are non-plain text, hashed using SHA-256.

### 2. Smart Data Handling
* **Binary Storage:** Daily logs are serialized objects for maximum integrity.
* **Logic:** Automated detection of membership expiry and fee calculation via the `Billable` interface.

### 3. Automated Reporting
* **Email Notifications:** Management receives professional HTML reports whenever a summary is generated.
* **Export Options:** One-click export of project folder structure and data reports to `.txt`.

### 4. Optimized UI/UX
* **Keyboard Navigation:** Support for `F1` (Home), `F2` (Records), `F3` (Reports), and `ESC` (Logout).
* **Modern Design:** Dark mode interface built with custom CSS.

---

## 🛠 Tech Stack
* **Language:** Java 17 (JDK 17)
* **Framework:** JavaFX 17 (Controls & FXML)
* **Build Tool:** Maven
* **Libraries:**
    * `jakarta.mail` (Email Automation)
    * `jbcrypt` (Password Security)

---

## ⚙️ Installation & Run
1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/avishka-d-rajapaksha/GymTracker-System.git](https://github.com/avishka-d-rajapaksha/GymTracker-System.git)
    ```
2.  **Open in NetBeans/IntelliJ**
    * Open the `GymTracker` project folder.
3.  **Build with Maven**
    * Run `mvn clean install` to setup dependencies.
4.  **Run the App**
    * Execute `Launcher.java` (The wrapper class to bypass JavaFX runtime checks).

---

## 👥 Contributors (Group 10)
* **Avishka D. Rajapaksha** - Backend Logic, Security & Email Service
* **Senuri Bagya** - UI/UX Design & Frontend
* **Ridmi Sathsarani** - Documentation & Testing
* **Chamathka Nivisadee** - Database & File Handling
