import React, {useEffect, useState} from "react";
import Navbar from "../Navbar/Navbar";
import "./Schedule.css"

function Schedule() {

    const [schedule, setSchedule] = useState({
        MONDAY: { startTime: "", endTime: "" },
        TUESDAY: { startTime: "", endTime: "" },
        WEDNESDAY: { startTime: "", endTime: "" },
        THURSDAY: { startTime: "", endTime: "" },
        FRIDAY: { startTime: "", endTime: "" }
    });

    const days = [
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY"
    ];

    const generateTime = () => {
        const times = [];
        for (let hour = 0; hour < 24; hour++) {
            for (let minute = 0; minute < 60; minute += 30) {
                const h = hour.toString().padStart(2, '0');
                const m = minute.toString().padStart(2, '0');
                times.push(`${h}:${m}`);
            }
        }
        return times;
    }

    const times = generateTime();
    useEffect(() => {
        getSchedule();
    }, []);



    const getSchedule = async () => {
        const response = await fetch(
            "http://localhost:8080/doctor/getSchedule",
            {
                headers:{
                    Authorization:`Bearer ${localStorage.getItem("token")}`
                }
            }
        );

        const data = await response.json();

        const newSchedule = {...schedule};

        data.forEach(item => {
            newSchedule[item.dayOfWeek] = {
                startTime: item.startTime,
                endTime: item.endTime
            };
        });
        setSchedule(newSchedule);
    }


    const [message, setMessage] = useState("");

    const handleChange = (day, field, value) => {
        setSchedule({
            ...schedule,
            [day]: {
                ...schedule[day],
                [field]: value
            }
        });
    };

    const saveSchedule = async (day) => {
        const response = await fetch(
            "http://localhost:8080/doctor/schedule",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                },

                body: JSON.stringify({
                    dayOfWeek: day,
                    startTime: schedule[day].startTime,
                    endTime: schedule[day].endTime
                })
            }
        );
        const text = await response.text();
        setMessage(text);
    }

    return (
        <div className="schedule-container">

            <Navbar />

            <div className="schedule-card">

                <h1>Doctor Schedule</h1>

                {days.map(day => (

                    <div className="schedule-row" key={day}>

                        <h3>{day}</h3>

                        <select
                            value={schedule[day].startTime.substring(0,5)}
                            onChange={(e) =>
                                handleChange(day, "startTime", e.target.value)
                            }
                        >

                            <option value="">Start Time</option>

                            {
                                times.map(time => (
                                    <option key={time} value={time}>
                                        {time}
                                    </option>
                                ))
                            }

                        </select>

                        <span> - </span>

                        <select
                            value={schedule[day].endTime.substring(0,5)}
                            onChange={(e) =>
                                handleChange(day, "endTime", e.target.value)
                            }
                        >

                            <option value="">End Time</option>

                            {
                                times.map(time => (
                                    <option key={time} value={time}>
                                        {time}
                                    </option>
                                ))
                            }

                        </select>

                        <button
                            onClick={() => saveSchedule(day)}
                        >
                            Save
                        </button>

                    </div>

                ))}

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

export default Schedule;

