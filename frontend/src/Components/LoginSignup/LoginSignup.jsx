import React, { useState } from 'react';
import './LoginSignup.css';

import { FaUser, FaEnvelope, FaLock } from "react-icons/fa";
import { useHistory } from "react-router-dom";

const LoginSignup = () => {
    const history = useHistory();

    const [action, setAction] = useState("Login");

    const [username, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");

    const handleSignUp = async () => {
        if (username.trim() === "" || email.trim() === "" || password.trim() === "") {
            setMessage("Please complete all fields!");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/auth/signup", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username,
                    password,
                    email
                })
            });

            if (response.ok) {
                setMessage("Account created successfully! Please login.");


                setName("");
                setEmail("");
                setPassword("");

                setAction("Login");
                setTimeout(() => {
                    setMessage("")
                }, 1500);
            } else {

                const message = await response.text();
                setMessage(message);
            }

        } catch (err) {
            setMessage("Server error!");
        }
    }

    const handleLogin = async () => {
        if (username.trim() === "" || password.trim() === "") {
            setMessage("Please enter your name and password!");
            return;
        }

        const response = await fetch("http://localhost:8080/auth/signin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username,
                password
            })
        });

        if (response.ok) {

            const data = await response.json();

            console.log(data)

            localStorage.setItem("token", data.token);
            localStorage.setItem("role", data.role);

            setMessage("Login successful!");

            if (data.role === "ROLE_DOCTOR") {
                history.push("/doctor");
            } else if (data.role === "ROLE_PATIENT") {
                history.push("/patient");
            }
        } else {
            const message = await response.text();
            setMessage(message);
        }
    }

    return (
        <div className="container">

            <div className="header">
                <div className="text">{action}</div>
                <div className="underline"></div>
            </div>

            {message && (
                <div className="success-message">
                    {message}
                </div>
            )}

            <div className="inputs">

                <div className="input">
                    <FaUser className="icon" />
                    <input
                        type="text"
                        placeholder="Name"
                        value={username}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                {action === "Sign Up" && (
                    <div className="input">
                        <FaEnvelope className="icon" />
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                )}

                <div className="input">
                    <FaLock className="icon" />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

            </div>

            {action === "Login" && (
                <div className="forgotpassword">
                    Lost Password? <span>Click Here!</span>
                </div>
            )}

            <div className="submit-container">

                <div
                    className={action === "Sign Up" ? "submit" : "submit gray"}
                    onClick={() => {
                        if (action === "Login") {
                            setAction("Sign Up");
                        } else {
                            handleSignUp();
                        }
                    }}
                >
                    {action === "Login" ? "Sign Up" : "Create Account"}
                </div>

                <div
                    className={action === "Login" ? "submit" : "submit gray"}
                    onClick={() => {
                        if (action === "Login") {
                            handleLogin();
                        } else {
                            setAction("Login");
                        }
                    }}
                >
                    {action === "Login" ? "Login" : "Back to Login"}
                </div>

            </div>

        </div>
    );
}

export default LoginSignup;