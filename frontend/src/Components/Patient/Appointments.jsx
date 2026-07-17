import React, { useEffect, useState } from "react";
import "./Appointments.css";
import Navbar from "../Navbar/Navbar";

const Appointment = () => {

    const [specialties,setSpecialties]=useState([]);
    const [selectedSpecialty,setSelectedSpecialty]=useState("");
    const [doctors,setDoctors]=useState([]);
    const [filteredDoctors,setFilteredDoctors]=useState([]);
    const [selectedDoctor,setSelectedDoctor]=useState(null);
    const [dateTime,setDateTime]=useState("");
    const [symptoms,setSymptoms]=useState("");
    const [message,setMessage]=useState("");

    useEffect(()=>{
        getDoctors();
    },[]);


    const getDoctors=async()=>{

        try{

            const token=localStorage.getItem("token");

            const res=await fetch("http://localhost:8080/api/doctors",{
                headers:{
                    Authorization:`Bearer ${token}`
                }
            });

            const data=await res.json();

            console.log("DOCTORS:",data);

            setDoctors(data);

            const specs=[...new Set(
                data.flatMap(d=>d.specialty?.map(s=>s.specialtyType)||[])
            )];

            setSpecialties(specs);

        }catch(e){
            console.log(e);
        }

    };


    const changeSpecialty=(value)=>{

        setSelectedSpecialty(value);
        setSelectedDoctor(null);

        const list=doctors.filter(d=>
            d.specialty?.some(s=>s.specialtyType===value)
        );

        setFilteredDoctors(list);

    };



    const createAppointment=async()=>{


        if(!selectedDoctor){
            setMessage("Select doctor first.");
            return;
        }


        if(!dateTime){
            setMessage("Select date.");
            return;
        }


        const year=Number(dateTime.substring(0,4));

        if(year<2025 || year>2100){
            setMessage("Invalid year.");
            return;
        }


        const data={

            doctorId:selectedDoctor.id,

            appointmentDateTime:dateTime,

            symptomsDescription:symptoms

        };


        console.log("SEND:",data);


        try{


            const token=localStorage.getItem("token");


            const res=await fetch(
                "http://localhost:8080/appointments/createAppointment",
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json",
                        Authorization:`Bearer ${token}`
                    },
                    body:JSON.stringify(data)
                }
            );


            const text=await res.text();

            setMessage(text);


        }catch(e){

            console.log(e);
            setMessage("Server error.");

        }


    };



    return (

        <>
            <Navbar/>

            <div className="appointment-page">

                <div className="appointment-container">


                    <h1>Create Appointment</h1>


                    <div className="field">

                        <label>Choose specialty</label>

                        <select
                            value={selectedSpecialty}
                            onChange={e=>changeSpecialty(e.target.value)}
                        >

                            <option value="">
                                Select specialty
                            </option>


                            {
                                specialties.map(s=>

                                    <option key={s} value={s}>
                                        {s}
                                    </option>

                                )
                            }


                        </select>

                    </div>



                    <div className="doctor-grid">


                        {

                            filteredDoctors.map(doctor=>

                                <div
                                    key={doctor.id}
                                    className={
                                        selectedDoctor?.id===doctor.id
                                            ?
                                            "doctor-card selected"
                                            :
                                            "doctor-card"
                                    }
                                >


                                    <h2>
                                        Dr. {doctor.username}
                                    </h2>


                                    <p>
                                        Email: {doctor.email}
                                    </p>


                                    <p>
                                        Experience: {doctor.experienceYears || 0} years
                                    </p>


                                    <button
                                        onClick={()=>{

                                            console.log("SELECTED:",doctor);

                                            setSelectedDoctor(doctor);

                                        }}
                                    >
                                        Choose Doctor
                                    </button>


                                </div>

                            )

                        }


                    </div>



                    {
                        selectedDoctor &&

                        <div className="appointment-form">


                            <h2>
                                Appointment with Dr. {selectedDoctor.username}
                            </h2>


                            <input

                                type="datetime-local"

                                min={new Date().toISOString().slice(0,16)}

                                value={dateTime}

                                onChange={e=>setDateTime(e.target.value)}

                            />



                            <textarea

                                placeholder="Symptoms description"

                                value={symptoms}

                                onChange={e=>setSymptoms(e.target.value)}

                            />



                            <button
                                onClick={createAppointment}
                            >
                                Book Appointment
                            </button>


                        </div>

                    }



                    {
                        message &&

                        <div className="message">
                            {message}
                        </div>

                    }



                </div>

            </div>

        </>

    );

};


export default Appointment;