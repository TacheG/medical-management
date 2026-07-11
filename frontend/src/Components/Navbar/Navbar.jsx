import React from "react";
import { Link } from "react-router-dom";
import LogoutButton from "../LogoutButton/LogoutButton";
import "./Navbar.css";

function Navbar() {
    const role = localStorage.getItem("role");

    return (
        <nav className="navbar">

            <div className="nav-links">
                <Link to="/profile">Profile</Link>

                <Link to="/history">Medical History</Link>

                {
                    role === "ROLE_PATIENT" && (
                        <Link to="/doctors">Doctors</Link>
                    )
                }

                {
                    role === "ROLE_PATIENT" && (
                        <Link to="/appointments">
                            Appointments
                        </Link>
                    )
                }

                {
                    role === "ROLE_DOCTOR" && (
                        <Link to="/schedule">
                            Schedule
                        </Link>
                    )
                }

                <Link to="/contact">
                    Contact
                </Link>


                <LogoutButton />
            </div>
        </nav>
    )
}

export default Navbar;