import React, {useEffect, useState} from "react";
import "./PatientProfile.css"
import Navbar from "../Navbar/Navbar";

function PatientProfile() {



    const [patient, setPatient] = useState(null);
    const [editMode, setEditMode] = useState(null);
    const [formData, setFormData] = useState({
        cnp: "",
        phoneNumber: "",
        bloodType: "",
        allergies: "",
        dateOfBirth: ""
    });

    const [message, setMessage] = useState("");

    useEffect(() => {
        getProfile();
    }, []);

    const getProfile = async() => {
        const response = await fetch("http://localhost:8080/patient/getProfile", {
            headers:{
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })


        if (response.ok) {
            const data = await response.json();

            console.log(data);

            setPatient(data);

            setFormData({
                cnp: data.cnp || "",
                phoneNumber: data.phoneNumber || "",
                bloodType: data.bloodType || "",
                allergies: data.allergies || "",
                dateOfBirth: data.dateOfBirth || ""
            });
        } else {
            setMessage("Could not load profile");
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const updateProfile = async () => {
        const response = await fetch(
            "http://localhost:8080/patient/profile",
            {
                method: "PUT",

                headers:{
                    "Content-Type":"application/json",
                    Authorization:`Bearer ${localStorage.getItem("token")}`
                },

                body: JSON.stringify(formData)
            }
        );

        const message = await response.text();
        setMessage(message);

        if (response.ok) {
            setEditMode(false);

            getProfile();
        }
    }

    if (!patient) return <h2>Patient not found</h2>

    return (

        <div className="profile-container">

            <Navbar/>


            <div className="profile-card">


                <h1>Patient Profile</h1>


                <div className="field">
                    <label>Username</label>
                    <input
                        value={patient.username}
                        disabled
                    />
                </div>


                <div className="field">
                    <label>Email</label>
                    <input
                        value={patient.email}
                        disabled
                    />
                </div>



                <div className="field">

                    <label>CNP</label>

                    <input
                        name="cnp"
                        value={formData.cnp}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                <div className="field">

                    <label>Phone Number</label>

                    <input
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                <div className="field">

                    <label>Blood Type</label>

                    <input
                        name="bloodType"
                        value={formData.bloodType}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                <div className="field">

                    <label>Allergies</label>

                    <input
                        name="allergies"
                        value={formData.allergies}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                <div className="field">

                    <label>Date of Birth</label>

                    <input
                        type="date"
                        name="dateOfBirth"
                        value={formData.dateOfBirth}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                {
                    editMode ? (

                        <button onClick={updateProfile}>
                            Save Changes
                        </button>

                    ) : (

                        <button onClick={() => setEditMode(true)}>
                            Edit Profile
                        </button>

                    )
                }


                {
                    message &&
                    <p className="message">
                        {message}
                    </p>
                }


            </div>


        </div>

    );

}


export default PatientProfile;