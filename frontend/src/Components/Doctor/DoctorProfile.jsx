import React, {useEffect, useState} from "react";
import "./DoctorProfile.css"
import Navbar from "../Navbar/Navbar";

function DoctorProfile() {

    const [doctor, setDoctor] = useState(null);;
    const [editMode, setEditMode] = useState(null);
    const [formData, setFormData] = useState({
        "licenseNumber": "",
        "biography": "",
        "experienceYears": ""
    });

    const [message, setMessage] = useState("");

    useEffect(() => {
        getProfile();
    }, []);

    const getProfile = async() => {
        const response = await fetch("http://localhost:8080/doctor/getProfile", {
            headers:{
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })


        if (response.ok) {
            const data = await response.json();

            console.log(data);

            setDoctor(data);

            setFormData({
                licenseNumber: data.licenseNumber || "",
                biography: data.biography || "",
                experienceYears: data.experienceYears || ""
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
            "http://localhost:8080/doctor/profile",
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

    if (!doctor) return <h2>Doctor not found</h2>

    return (

        <div className="profile-container">

            <Navbar/>


            <div className="profile-card">


                <h1>Doctor Profile</h1>


                <div className="field">
                    <label>Username</label>
                    <input
                        value={doctor.username}
                        disabled
                    />
                </div>


                <div className="field">
                    <label>Email</label>
                    <input
                        value={doctor.email}
                        disabled
                    />
                </div>

                <div className="field">

                    <label>Biography</label>

                    <input
                        name="biography"
                        value={formData.biography}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>


                <div className="field">

                    <label>License Number</label>

                    <input
                        name="licenseNumber"
                        value={formData.licenseNumber}
                        disabled={!editMode}
                        onChange={handleChange}
                    />

                </div>



                <div className="field">

                    <label>Experience Years</label>

                    <input
                        name="experienceYears"
                        value={formData.experienceYears}
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


export default DoctorProfile;