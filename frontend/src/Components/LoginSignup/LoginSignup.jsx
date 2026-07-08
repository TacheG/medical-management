import React, { useState } from 'react';
import './LoginSignup.css';

import { FaUser, FaEnvelope, FaLock } from "react-icons/fa";
import {renderToPipeableStream} from "react-dom/server";

const LoginSignup = () => {

    const [action, setAction] = useState("Login");

    const [username, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSignUp = async () => {
        if (username.trim() === "" || email.trim() === "" || password.trim() === "") {
            alert("Please complete all fields!");
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
                alert("Account created successfully!");

                setAction("Login");

                setName("");
                setEmail("");
                setPassword("");

            } else {

                const message = await response.text();
                alert(message);
            }

        } catch (err) {
            alert("Server error!");
        }
    }

    const handleLogin = async () => {
        if (username.trim() === "" || password.trim() === "") {
            alert("Please enter your name and password!");
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

            const token = await response.text();

            localStorage.setItem("token", token);

            alert("Login successful!");
        } else {
            const message = await response.text();
            alert(message);
        }
    }

    return (
        <div className="container">

            <div className="header">
                <div className="text">{action}</div>
                <div className="underline"></div>
            </div>

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