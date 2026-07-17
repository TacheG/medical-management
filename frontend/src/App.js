import './App.css';
import LoginSignup from "./Components/LoginSignup/LoginSignup";
import PatientPage from "./Components/Patient/PatientPage"
import DoctorPage from "./Components/Doctor/DoctorPage"
import ProtectedRoute from "./Components/ProtectedRoute/ProtectedRoute";
import PatientProfile from "./Components/Patient/PatientProfile";
import DoctorProfile from "./Components/Doctor/DoctorProfile";
import Schedule from "./Components/Doctor/Schedule"
import Doctors from "./Components/Patient/Doctors";
import MedicalHistory from "./Components/Patient/MedicalHistory";
import Appointments from "./Components/Patient/Appointments";

import { BrowserRouter, Switch } from "react-router-dom";
function App() {
  return (
      <BrowserRouter>
          <Switch>

              <ProtectedRoute
                  exact
                  path="/"
                  component={LoginSignup}
                  allowedRole={null}
              />

              <ProtectedRoute
                  exact
                  path="/patient"
                  component={PatientPage}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  exact
                  path="/doctor"
                  component={DoctorPage}
                  allowedRole="ROLE_DOCTOR"
              />

              <ProtectedRoute
                  exact
                  path="/doctors"
                  component={Doctors}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  exact
                  path="/medical-history"
                  component={MedicalHistory}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  exact
                  path="/profile"
                  component={PatientProfile}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  exact
                  path="/appointments"
                  component={Appointments}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  exact
                  path="/doctorProfile"
                  component={DoctorProfile}
                  allowedRole="ROLE_DOCTOR"
              />

              <ProtectedRoute
                  exact
                  path="/schedule"
                  component={Schedule}
                  allowedRole="ROLE_DOCTOR"
              />

          </Switch>
      </BrowserRouter>
  );
}

export default App;
