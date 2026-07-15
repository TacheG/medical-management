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

    const specialties = [
        "CARDIOLOGY",
        "DERMATOLOGY",
        "NEUROLOGY",
        "ORTHOPEDICS",
        "PEDIATRICS",
        "GYNECOLOGY",
        "OPHTHALMOLOGY",
        "ENT",
        "UROLOGY",
        "GASTROENTEROLOGY",
        "ENDOCRINOLOGY",
        "PULMONOLOGY",
        "PSYCHIATRY",
        "ONCOLOGY",
        "RADIOLOGY",
        "ANESTHESIOLOGY",
        "GENERAL_SURGERY",
        "PLASTIC_SURGERY",
        "EMERGENCY_MEDICINE",
        "FAMILY_MEDICINE"
    ];

    const [doctorSpecialties, setDoctorSpecialties] = useState([]);

    const [specialtyData, setSpecialtyData] = useState({
        specialtyType: "",
        price: ""
    });

    const [message, setMessage] = useState("");

    useEffect(() => {
        getProfile();
        getSpecialties();
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
    const handleSpecialtyChange = (e) => {

        setSpecialtyData({
            ...specialtyData,
            [e.target.name]: e.target.value
        });

    }

    const addSpecialty = async () => {

        const response = await fetch(
            "http://localhost:8080/doctor/addSpecialty",
            {
                method:"POST",

                headers:{
                    "Content-Type":"application/json",
                    Authorization:`Bearer ${localStorage.getItem("token")}`
                },

                body:JSON.stringify(specialtyData)
            }
        );

        const text = await response.text();
        setMessage(text);

        if(response.ok){

            setSpecialtyData({
                specialtyType:"",
                price:""
            });

            getSpecialties();
        }
    }

    const getSpecialties = async () => {

        const response = await fetch(
            "http://localhost:8080/doctor/specialties",
            {
                headers:{
                    Authorization:`Bearer ${localStorage.getItem("token")}`
                }
            }
        );

        if(response.ok){
            const data = await response.json();
            setDoctorSpecialties(data);
        }

    }

    const deleteSpecialty = async (id) => {

        const response = await fetch(
            `http://localhost:8080/doctor/specialty/${id}`,
            {
                method:"DELETE",
                headers:{
                    Authorization:
                        `Bearer ${localStorage.getItem("token")}`
                }
            }
        );


        const text = await response.text();

        setMessage(text);


        if(response.ok){
            getSpecialties();
        }

    }

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

            <div className="specialty-section">

                <h2>Specialties</h2>

                <div className="specialty-form">

                    <div className="field">
                        <label>Specialty</label>

                        <select
                            name="specialtyType"
                            value={specialtyData.specialtyType}
                            onChange={handleSpecialtyChange}
                        >
                            <option value="">
                                Select specialty
                            </option>

                            {
                                specialties.map(specialty => (
                                    <option
                                        key={specialty}
                                        value={specialty}
                                    >
                                        {specialty.replaceAll("_"," ")}
                                    </option>
                                ))
                            }

                        </select>

                    </div>


                    <div className="field">

                        <label>Price</label>

                        <input
                            type="number"
                            name="price"
                            placeholder="Price (RON)"
                            value={specialtyData.price}
                            onChange={handleSpecialtyChange}
                        />

                    </div>


                    <button
                        className="add-specialty-button"
                        onClick={addSpecialty}
                    >
                        Add Specialty
                    </button>

                </div>


                <h3>Current Specialties</h3>


                <div className="specialty-list">

                    {
                        doctorSpecialties.length === 0 ? (

                            <p>No specialties added.</p>

                        ) : (

                            doctorSpecialties.map((specialty) => (

                                <div
                                    className="specialty-card"
                                    key={specialty.id}
                                >

                                    <div>
                                        <strong>
                                            {specialty.specialtyType.replaceAll("_"," ")}
                                        </strong>

                                        <p>
                                            {specialty.price} RON
                                        </p>
                                    </div>


                                    <button
                                        className="delete-specialty-button"
                                        onClick={() => deleteSpecialty(specialty.id)}
                                    >
                                        Delete
                                    </button>

                                </div>

                            ))

                        )
                    }

                </div>

            </div>


            <div className="profile-card">


                <h1>Doctor Profile</h1>

                <hr />

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