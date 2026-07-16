import React from "react";
import Navbar from "../Navbar/Navbar";
import "./PatientPage.css";

function PatientPage(){
    return(
        <div className="patient-page">
            <Navbar/>
            <h1>Patient Dashboard</h1>
        </div>
    );
}

export default PatientPage;