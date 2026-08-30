# Medical Management Project

- Tache George-Sebastian
- Constantinescu Alexandru-Marius

## Authors

- [@TacheG](https://github.com/TacheG)
- [@Ale](https://github.com/aleconst)

## Desired result

- Develop a medical clinic website.
- Implement core clinic actions:
  - Register patient accounts and manage requests to become a doctor.
  - Manage patient profiles and medical information.
  - Manage doctor profiles, specialties, and consultation prices.
  - Browse doctors and filter them by specialty.
  - Create appointments as a patient and update their status as a doctor.
- Authentication and authorization based on user roles.

## Technologies

- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA.
- **Frontend:** React, CSS.
- **Database:** MySQL.
- **Authentication:** JWT.
- **Development tools:** Gradle, Docker.

## Main components

### Backend

#### Entities - contains the database entities and enums.

- **User**: stores account information such as username, hashed password, email and role.
- **Patient**: stores patient information such as CNP, phone number, date of birth, blood type, and allergies. Each patient profile is linked to a user account.
- **Doctor**: stores professional information such as license number, biography, experience, and approval status. Each doctor profile is linked to a user account.
- **DoctorSpecialty**: associates a doctor with a medical specialty and its consultation price.
- **DoctorSchedule**: stores the working hours of a doctor for each day of the week.
- **Appointment**: associates a patient with a doctor and stores the date, time, status, symptoms description, and associated medical record.
- **MedicalRecord**: stores a diagnosis, treatment, and doctor notes for an appointment.
- **AuthRole**: defines the backend authorization roles Admin and User.
- **DoctorStatus**: defines PENDING, APPROVED, and REJECTED.
- **AppointmentStatus**: defines PENDING, CONFIRMED, CANCELLED, and COMPLETED.
- **SpecialtyType**: defines all supported medical specialties.

#### Controller - exposes the REST endpoints.

- **AuthController**: registers users with a patient profile and authenticates existing accounts.
- **UserController**: receives requests to become a doctor.
- **AdminController**: provides administrator-only endpoints to approve a doctor or revoke doctor status.
- **PatientController**: retrieves and updates patient profiles and retrieves medical history.
- **DoctorController**: retrieves and updates doctor profiles, manages specialties and schedules, and shows schedule-based time slots.
- **AppointmentController**: creates appointments, retrieves the current user's appointments, and receives appointment status updates.
- **MedicalRecordController**: receives medical-record creation requests and retrieves the current patient's medical history.
- **UtilsController**: returns the doctor list used by the frontend.

#### Service - contains application logic.

- **UserService**: handles doctor requests, approval, and removal of doctor status.
- **PatientService**: validates and updates patient profile details and retrieves medical records.
- **DoctorService**: handles doctor profiles, specialties, weekly schedules, time-slot generation, and medical-record creation and retrieval.
- **AppointmentService**: checks doctor working hours and existing bookings, creates and retrieves appointments.
- **CustomUserDetailsService**: loads credentials and backend authorization roles for Spring Security.

#### Repository - contains Spring Data JPA interfaces for database access.

- **UserRepository**: retrieves users and accounts with a pending doctor request.
- **PatientRepository**: retrieves patient profiles.
- **DoctorRepository**: retrieves doctor profiles.
- **DoctorScheduleRepository**: retrieves schedules by doctor and day of the week.
- **DoctorSpecialtyRepository**: retrieves specialties and checks for duplicate doctor-specialty combinations.
- **AppointmentRepository**: retrieves appointments and checks whether a doctor already has an appointment at a specific date and time.
- **MedicalRecordRepository**: retrieves medical records through the appointment's patient account.

#### Security - configures authentication and access control.

- **WebSecurityConfig**: configures the security filter chain, password encoding, and much more.
- **JwtUtil**: generates and validates JWT tokens and extracts usernames.
- **AuthTokenFilter**: reads Bearer tokens and populates the security context.
- **AuthEntryPointJwt**: returns an unauthorized response for unauthenticated access to protected resources.
- **AdminInitializer**: creates a default development administrator account if it doesn't exist.

#### DTO - defines data returned by profile, schedule, specialty, appointment, and medical-record endpoints.

- **PatientDto**, **DoctorDto**, **DoctorSpecialtyDto**, **DoctorScheduleDto**, **AppointmentDto**, and **MedicalRecordDto**.

#### Request - defines request bodies received by the API.

- **AuthRequest**, **PatientProfileRequest**, **DoctorRequest**, **DoctorScheduleRequest**, **DoctorSpecialtyRequest**, **AppointmentRequest**, **AppointmentStatusRequest**, and **MedicalRecordRequest**.

#### Response

- **AuthResponse**: returns the JWT token and the patient / doctor label used by the frontend.

#### Exception

- **UserAlreadyExistsException**: signals an attempt to register an existing username.
- **GlobalExceptionHandler**: handles duplicate usernames, invalid credentials, and other exceptions.

#### BackendApplication - starts the Spring Boot application.

### Frontend

#### Components /LoginSignup

- **LoginSignup**: provides login and registration forms, stores the token and frontend role in local storage, and redirects users after login.
- **ContactPage**: displays contact information, FAQs, and a demonstration contact form.

#### Components /Patient

- **PatientPage**: displays the patient dashboard heading and navigation.
- **PatientProfile**: displays account information and edits CNP, phone number, blood type, allergies, and date of birth.
- **Doctors**: displays doctor information and consultation prices, with a specialty filter.
- **Appointments**: provides the appointment creation form, including specialty filtering, doctor selection, date and time, and symptoms.
- **MedicalHistory**: displays the patient's appointment-linked diagnoses, treatments, and doctor notes.

#### Components /Doctor

- **DoctorPage**: displays the doctor dashboard heading and navigation.
- **DoctorProfile**: displays professional information, updates biography and experience, and allows specialties to be added with a price or deleted.
- **Schedule**: provides working-hour configuration for Monday through Friday, using time selections in 30 minute increments.

#### Components /Navbar

- **Navbar**: displays links according to the frontend role and clears local authentication data on logout.

#### Components /ProtectedRoute

- **ProtectedRoute**: checks the locally stored token and role to control navigation and redirect users.

#### App.js - defines the application routes.

#### Index.js - mounts the React application.
