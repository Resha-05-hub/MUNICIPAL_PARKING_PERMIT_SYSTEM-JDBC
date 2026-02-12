# 🚗 Municipal Parking Permit Application & Violation Tracking System

A console-based Java application designed to assist municipal authorities in managing residential and commercial parking permits while tracking vehicle violations and fines using JDBC.

---

## 📋 Project Overview
The system provides a centralized platform to manage the full lifecycle of parking permits. It uses an optimized design with two primary tables where violations and fines are aggregated directly at the permit.

### Core Operations
**Register New Permit Holder**: Captures profiles, addresses, and contact details with an ACTIVE/INACTIVE status.
**View Permit Holders**: Fetches specific holder details or lists all registered holders for reporting.
**Apply for New Permit**: A transactional process that validates holder status and prevents duplicate active permits for the same vehicle and zone.
**Renew Parking Permit**: Extends permit validity dates transactionally while ensuring the permit is not cancelled.
**Record Violation & Fine**: Atomic update that increments violation counts and updates outstanding fines for active permits.
**Safe Deactivation/Removal**: Prevents removal of holders who still have active permits or unpaid fines.

---

## 🛠️ Technical Architecture
The project follows a modular layered architecture:

| Layer | Package | Responsibility |
| :--- | :--- | :--- |
| **Presentation** | `com.parking.app` | `ParkingMain` console menu and user input handling. |
| **Service** | `com.parking.service` | Business rules, validation, and transaction management. |
| **Data Access** | `com.parking.dao` | Encapsulates all SQL/JDBC logic for database operations. |
| **Model** | `com.parking.bean` | Java Beans (`PermitHolder`, `ParkingPermit`) mapping to tables. |
| **Utility** | `com.parking.util` | Connection management and custom exception handling. |

---

## 🗄️ Database Design
The system is designed for an Oracle-based schema:

### 1. PERMIT_HOLDER_TBL
Stores master data for citizens or organizations.
**Permit Holder ID**: Primary Key (e.g., PH1001).
**Holder Type**: RESIDENT, COMMERCIAL, or GOVT DEPT.
**Status**: ACTIVE or INACTIVE.

### 2. PARKING_PERMIT_TBL
Tracks transactional records for individual vehicle permits.
**Permit ID**: Primary Key (e.g., PMT2025-001).
**Violation Count**: Aggregated number of violations.
**Outstanding Fine Amount**: Total unpaid fines for the permit.
**Status**: ACTIVE, EXPIRED, or CANCELLED.

---

## ⚠️ Custom Exceptions
The application uses specific exceptions to enforce business integrity:
**ValidationException**: Thrown for invalid input data or date logic.
**FineProcessingException**: Triggered by issues during violation updates on non-active permits.
**ActivePermitOrFineExistsException**: Prevents deleting holders with active obligations.

---

## 🚦 Getting Started
1.**Database Setup**: Create a user `parking_user` with `CONNECT` and `RESOURCE` privileges.
2.**Connectivity**: Configure `DBUtil` with the correct JDBC URL and credentials.
3.**Execution**: Run `ParkingMain.java` to access the interactive console menu.

---

## 📸 Project Output Screenshots

---

### 1. Console Output (Java Execution)
![Java Console Output](https://github.com/user-attachments/assets/e5c4548a-850e-46e8-8a02-ede1f73dd73f)

### 2. Database Verification (Oracle SQL)

**Permit Holder Master Records**
![Permit Holder Table](https://github.com/user-attachments/assets/40cd7cfc-4c89-4cf5-89ca-d7459e9fa6c6)

**Parking Permit Transaction Records**
![Parking Permit Table](https://github.com/user-attachments/assets/4ba813af-2399-4b2a-8fc3-2cbbfbcc3f0a)


