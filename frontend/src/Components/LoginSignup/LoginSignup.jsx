import React, { useState } from "react";
import "./LoginSignup.css";
import { FaUser, FaEnvelope, FaLock, FaHeartbeat } from "react-icons/fa";
import { useHistory } from "react-router-dom";

const LoginSignup = () => {

    const history = useHistory();

    const [action, setAction] = useState("Login");

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const clearMessages = () => {
        setMessage("");
        setError("");
    };

    const handleSignUp = async () => {

        clearMessages();

        if (!username || !email || !password) {
            setError("Please complete all fields!");
            return;
        }

        try {

            const response = await fetch(
                "http://localhost:8080/auth/signup",
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify({
                        username,
                        email,
                        password
                    })
                }
            );

            if(response.ok){

                setMessage("Account created successfully! Please login.");

                setUsername("");
                setEmail("");
                setPassword("");

                setTimeout(()=>{
                    setAction("Login");
                    clearMessages();
                },2000);

            }else{
                setError(await response.text());
            }

        }catch{
            setError("Server error!");
        }
    };


    const handleLogin = async () => {

        clearMessages();

        if(!username || !password){
            setError("Please enter username and password!");
            return;
        }

        try{

            const response = await fetch(
                "http://localhost:8080/auth/signin",
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify({
                        username,
                        password
                    })
                }
            );


            if(response.ok){

                const data = await response.json();

                localStorage.setItem("token", data.token);
                localStorage.setItem("role", data.role);

                setMessage("Login successful!");


                setTimeout(()=>{

                    if(data.role==="ROLE_DOCTOR"){
                        history.push("/doctor");
                    }

                    if(data.role==="ROLE_PATIENT"){
                        history.push("/patient");
                    }

                },700);


            }else{

                setError(await response.text());

            }


        }catch{

            setError("Server error!");

        }

    };


    return (

        <div className="login-page">

            <div className="container">

                <div className="header">

                    <div className="logo">
                        <FaHeartbeat/>
                    </div>

                    <div className="text">
                        MediCare
                    </div>

                    <div className="subtitle">
                        Your health, our priority
                    </div>

                    <div className="underline"></div>

                </div>


                {message &&
                    <div className="success-message">
                        {message}
                    </div>
                }


                {error &&
                    <div className="success-message error-message">
                        {error}
                    </div>
                }


                <div className="inputs">

                    <div className="input">

                        <FaUser className="icon"/>

                        <input
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={(e)=>setUsername(e.target.value)}
                        />

                    </div>


                    {action==="Sign Up" &&

                        <div className="input">

                            <FaEnvelope className="icon"/>

                            <input
                                type="email"
                                placeholder="Email"
                                value={email}
                                onChange={(e)=>setEmail(e.target.value)}
                            />

                        </div>

                    }


                    <div className="input">

                        <FaLock className="icon"/>

                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e)=>setPassword(e.target.value)}
                        />

                    </div>


                </div>


                {action==="Login" &&

                    <div className="forgotpassword">
                        Forgot password? <span>Click here</span>
                    </div>

                }


                <div className="submit-container">


                    <div
                        className={action==="Sign Up" ? "submit" : "submit gray"}
                        onClick={() =>
                            action==="Login"
                                ? setAction("Sign Up")
                                : handleSignUp()
                        }
                    >

                        {action==="Login" ? "Create Account" : "Register"}

                    </div>



                    <div
                        className={action==="Login" ? "submit" : "submit gray"}
                        onClick={() =>
                            action==="Login"
                                ? handleLogin()
                                : setAction("Login")
                        }
                    >

                        {action==="Login" ? "Login" : "Back"}

                    </div>


                </div>


            </div>

        </div>

    );

};

export default LoginSignup;