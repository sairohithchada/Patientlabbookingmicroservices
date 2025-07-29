# 🏥 patient booking service deliverables

---

## ✅ 1. ERD Diagram

```mermaid
erDiagram
    PATIENTS {
        int patientid PK
        string fname
        string lname
        date dob
        string gender
        string phonenumber
        string email
        string createdat
    }

    PATIENT_ADDRESS {
        UUID addressid PK
        int patientid FK
        string addressline1
        string addresstype
        string city
        string state
        string country
        string zipcode
        boolean is_primary
        decimal latitude
        decimal longitude
    }

    LABS {
        int labid PK
        string labname
        string addressline1
        string city
        string state
        string country
        string zipcode
        decimal latitude
        decimal longitude
        timestamp createdat
    }

    LAB_SLOTS {
        int slotid PK
        int labid FK
        date slot_date
        string session
        time slot_time
        string status
        timestamp createdat
    }

    APPOINTMENTS {
        int appointmentid PK
        int patientid FK
        int labid FK
        int slotid FK
        string appointmentType
        date appointmentDate
        time appointmentTime
    }

    PATIENTS ||--o{ PATIENT_ADDRESS : has
    PATIENTS ||--o{ APPOINTMENTS : books
    LABS ||--o{ LAB_SLOTS : contains
    LABS ||--o{ APPOINTMENTS : schedules
    LAB_SLOTS ||--|| APPOINTMENTS : used_by

```

---

## ✅ 3. C2 Architecture (Container View)

graph TD
    User((User))

    subgraph Aggregator Layer
        AggregatorService[Aggregator Service<br><sub>Orchestrates Booking Workflow</sub>]
    end

    subgraph Microservices
        PatientService[Patient Service<br><sub>Handles patient data & addresses</sub>]
        LabService[Lab Service<br><sub>Handles labs & slots</sub>]
        AppointmentService[Appointment Service<br><sub>Books appointments</sub>]
        DistanceService[Distance Service<br><sub>Calculates nearest labs</sub>]
    end

    subgraph External APIs
        Nominatim[Nominatim API<br><sub>Geolocation API</sub>]
    end

    subgraph Databases
        PatientDB[(PostgreSQL - Patient DB)]
        LabDB[(PostgreSQL - Lab DB)]
        AppointmentDB[(PostgreSQL - Appointment DB)]
    end

    User --> AggregatorService

    AggregatorService --> PatientService
    AggregatorService --> LabService
    AggregatorService --> AppointmentService
    AggregatorService --> DistanceService

    PatientService --> PatientDB
    LabService --> LabDB
    AppointmentService --> AppointmentDB
    DistanceService --> Nominatim






