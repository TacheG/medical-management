import './App.css';
import LoginSignup from "./Components/LoginSignup/LoginSignup";
import PatientPage from "./Components/Patient/PatientPage"
import DoctorPage from "./Components/Doctor/DoctorPage"
import ProtectedRoute from "./Components/ProtectedRoute/ProtectedRoute";
import PatientProfile from "./Components/Patient/PatientProfile";
import DoctorProfile from "./Components/Doctor/DoctorProfile";

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
                  path="/patient"
                  component={PatientPage}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  path="/doctor"
                  component={DoctorPage}
                  allowedRole="ROLE_DOCTOR"
              />

              <ProtectedRoute
                  path="/profile"
                  component={PatientProfile}
                  allowedRole="ROLE_PATIENT"
              />

              <ProtectedRoute
                  path="/doctorProfile"
                  component={DoctorProfile}
                  allowedRole="ROLE_DOCTOR"
              />

          </Switch>
      </BrowserRouter>
  );
}

export default App;
