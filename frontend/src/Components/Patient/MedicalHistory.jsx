import React, {useEffect, useState} from "react";
import "./MedicalHistory.css";
import Navbar from "../Navbar/Navbar";


const MedicalHistory = () => {

    const [records,setRecords] = useState([]);
    const [loading,setLoading] = useState(true);
    const [error,setError] = useState("");


    useEffect(()=>{

        const fetchMedicalHistory = async()=>{

            try{

                const token = localStorage.getItem("token");


                const response = await fetch(
                    "http://localhost:8080/medical-records/my-history",
                    {
                        method:"GET",
                        headers:{
                            "Content-Type":"application/json",
                            Authorization:`Bearer ${token}`,
                        }
                    }
                );


                if(!response.ok){
                    throw new Error(`Error ${response.status}`);
                }


                const data = await response.json();

                setRecords(data);


            }catch(err){

                console.error(err);
                setError("Could not load medical history.");

            }finally{

                setLoading(false);

            }

        };


        fetchMedicalHistory();

    },[]);



    return(

        <>

            <Navbar/>


            <div className="history-page">


                <div className="history-content">


                    <div className="history-header">

                        <h1>
                            Medical History
                        </h1>

                    </div>



                    {
                        loading &&
                        <p className="loading">
                            Loading medical history...
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
                        records.length===0 &&
                        <p className="no-records">
                            No medical records found.
                        </p>
                    }



                    <div className="medical-record-list">


                        {
                            records.map((record)=>(


                                <div
                                    className="medical-record-card"
                                    key={record.id}
                                >


                                    <h2>
                                        🩺 {record.doctorName}
                                    </h2>



                                    <p>
                                        <strong>
                                            📅 Date:
                                        </strong>{" "}
                                        {
                                            new Date(record.appointmentDate)
                                                .toLocaleString()
                                        }
                                    </p>



                                    <hr/>



                                    <p>
                                        <strong>
                                            🔎 Diagnosis:
                                        </strong>
                                    </p>

                                    <p>
                                        {record.diagnosis}
                                    </p>




                                    <p>
                                        <strong>
                                            💊 Treatment:
                                        </strong>
                                    </p>

                                    <p>
                                        {record.treatment}
                                    </p>




                                    <p>
                                        <strong>
                                            📝 Doctor notes:
                                        </strong>
                                    </p>

                                    <p>
                                        {record.doctorNotes}
                                    </p>



                                </div>


                            ))
                        }


                    </div>


                </div>


            </div>


        </>

    );

};


export default MedicalHistory;