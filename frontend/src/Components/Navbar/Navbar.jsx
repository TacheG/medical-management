import React from "react";
import {Link, useHistory} from "react-router-dom";
import "./Navbar.css";

function Navbar() {
    const role = localStorage.getItem("role");

    const history = useHistory();

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        history.push("/");
    };

    return (
        <nav className="navbar">

            <div className="nav-links">

                {
                    role === "ROLE_PATIENT" && (
                        <Link to={"/patient"}>Menu</Link>
                    )
                }

                {
                    role === "ROLE_DOCTOR" && (
                        <Link to={"/doctor"}>Menu</Link>
                    )
                }

                {
                    role === "ROLE_PATIENT" && (
                        <Link to="/profile">Profile</Link>
                    )
                }

                {
                    role === "ROLE_DOCTOR" && (
                        <Link to="/doctorProfile">Profile</Link>
                    )
                }

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

                <button
                    className="logout-button"
                    onClick={handleLogout}
                >
                    Logout
                </button>
            </div>
        </nav>
    )
}

export default Navbar;