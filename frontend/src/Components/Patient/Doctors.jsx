import React, {useEffect, useState} from "react";
import "./Doctors.css";
import Navbar from "../Navbar/Navbar";

const Doctors = () => {
    const [doctors,setDoctors] = useState([]);
    const [filteredDoctors,setFilteredDoctors] = useState([]);
    const [specialties,setSpecialties] = useState([]);
    const [selectedSpecialty,setSelectedSpecialty] = useState("All");
    const [loading,setLoading] = useState(true);
    const [error,setError] = useState("");

    useEffect(()=>{
        const fetchDoctors = async()=>{

            try{
                const token = localStorage.getItem("token");

                const response = await fetch(
                    "http://localhost:8080/api/doctors",
                    {
                        method:"GET",
                        headers:{
                            "Content-Type":"application/json",
                            Authorization:`Bearer ${token}`,
                        },
                    }
                );

                if(!response.ok){
                    throw new Error(`Error: ${response.status}`);
                }

                const data = await response.json();

                setDoctors(data);
                setFilteredDoctors(data);

                const allSpecialties = data.flatMap((doctor)=>
                    doctor.specialty?.map(
                        (s)=>s.specialtyType
                    ) || []
                );

                setSpecialties([
                    "All",
                    ...new Set(allSpecialties)
                ]);

            }catch(err){
                console.error(err);
                setError("Could not load doctors.");
            }finally{
                setLoading(false);
            }
        };

        fetchDoctors();
    },[]);


    useEffect(()=>{

        if(selectedSpecialty==="All"){
            setFilteredDoctors(doctors);
        }else{

            const filtered = doctors.filter((doctor)=>
                doctor.specialty?.some(
                    (s)=>s.specialtyType===selectedSpecialty
                )
            );

            setFilteredDoctors(filtered);
        }

    },[selectedSpecialty,doctors]);


    return(
        <>
            <Navbar/>

            <div className="doctors-page">

                <div className="doctors-content">

                    <div className="doctors-header">

                        <h1>
                            Doctors
                        </h1>

                        <div className="filter-box">

                            <label>
                                Choose specialty:
                            </label>

                            <select
                                value={selectedSpecialty}
                                onChange={(e)=>
                                    setSelectedSpecialty(e.target.value)
                                }
                            >

                                {
                                    specialties.map(
                                        (specialty,index)=>(
                                            <option
                                                key={index}
                                                value={specialty}
                                            >
                                                {specialty}
                                            </option>
                                        )
                                    )
                                }

                            </select>

                        </div>

                    </div>


                    {
                        loading &&
                        <p className="loading">
                            Loading doctors...
                        </p>
                    }


                    {
                        error &&
                        <p className="error">
                            {error}
                        </p>
                    }


                    {
                        !loading &&
                        filteredDoctors.length===0 &&
                        <p className="no-doctors">
                            No doctors available.
                        </p>
                    }


                    <div className="doctor-list">

                        {
                            filteredDoctors.map((doctor,index)=>(

                                <div
                                    className="doctor-card"
                                    key={index}
                                >

                                    <h2>
                                        👨‍⚕️ Dr. {doctor.username}
                                    </h2>


                                    <p>
                                        <strong>
                                            📧 Email:
                                        </strong>{" "}
                                        {doctor.email}
                                    </p>


                                    <p>
                                        <strong>
                                            📄 License:
                                        </strong>{" "}
                                        {doctor.licenseNumber}
                                    </p>


                                    <p>
                                        <strong>
                                            🩺 Experience:
                                        </strong>{" "}
                                        {doctor.experienceYears ?? "Not specified"}
                                    </p>


                                    {
                                        doctor.biography &&
                                        <p>
                                            <strong>
                                                📝 Biography:
                                            </strong>{" "}
                                            {doctor.biography}
                                        </p>
                                    }


                                    <hr/>


                                    <h3>
                                        Specialties
                                    </h3>


                                    {
                                        doctor.specialty?.length>0 ?

                                            doctor.specialty.map(
                                                (specialty)=>(
                                                    <div
                                                        key={specialty.id}
                                                        className="specialty-item"
                                                    >

                                                        <p>
                                                            <strong>
                                                                {specialty.specialtyType}
                                                            </strong>
                                                        </p>

                                                        <p>
                                                            Consultation: {specialty.price} lei
                                                        </p>

                                                    </div>
                                                )
                                            )

                                            :

                                            <p>
                                                No specialties
                                            </p>
                                    }


                                </div>

                            ))
                        }

                    </div>

                </div>

            </div>
        </>
    );
};

export default Doctors;