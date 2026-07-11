import React from "react";
import { useHistory } from "react-router-dom";
import "./LogoutButton.css";


function LogoutButton() {

    const history = useHistory();

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        history.push("/");
    };


    return (
        <button
            className="logout-button"
            onClick={handleLogout}
        >
            Logout
        </button>
    );
}

export default LogoutButton;