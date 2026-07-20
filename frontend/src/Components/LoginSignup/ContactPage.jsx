import React, { useState } from "react";
import "./ContactPage.css";
import Navbar from "../Navbar/Navbar";

function ContactPage() {
    const [form, setForm] = useState({
        name: "",
        email: "",
        subject: "",
        message: ""
    });

    const [success, setSuccess] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const sendMessage = () => {
        setSuccess("Your message has been sent successfully!");

        setForm({
            name: "",
            email: "",
            subject: "",
            message: ""
        });
    };

    return (

        <div className="contact-container">

            <Navbar/>

            <div className="contact-card">

                <h1>Contact & Support</h1>

                <p className="subtitle">
                    Need assistance? Our team is here to help.
                </p>

                <div className="info-grid">

                    <div className="info-box">
                        <h3>Phone</h3>
                        <p>+40 721 123 456</p>
                    </div>

                    <div className="info-box">
                        <h3>Email</h3>
                        <p>support@medicare.com</p>
                    </div>

                    <div className="info-box">
                        <h3>Address</h3>
                        <p>Bucharest, Romania</p>
                    </div>

                    <div className="info-box">
                        <h3>Working Hours</h3>
                        <p>Mon - Fri</p>
                        <p>08:00 - 18:00</p>
                    </div>

                </div>

                <div className="support-form">

                    <h2>Send us a message</h2>

                    <input
                        type="text"
                        placeholder="Full Name"
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                    />

                    <input
                        type="email"
                        placeholder="Email"
                        name="email"
                        value={form.email}
                        onChange={handleChange}
                    />

                    <input
                        type="text"
                        placeholder="Subject"
                        name="subject"
                        value={form.subject}
                        onChange={handleChange}
                    />

                    <textarea
                        rows="5"
                        placeholder="Message..."
                        name="message"
                        value={form.message}
                        onChange={handleChange}
                    />

                    <button onClick={sendMessage}>
                        Send Message
                    </button>

                    {
                        success &&
                        <p className="success">
                            {success}
                        </p>
                    }

                </div>

                <div className="faq">

                    <h2>Frequently Asked Questions</h2>

                    <div className="faq-item">
                        <h4>How do I book an appointment?</h4>
                        <p>Select a doctor, choose a specialty and an available time slot.</p>
                    </div>

                    <div className="faq-item">
                        <h4>Can I cancel an appointment?</h4>
                        <p>Yes. You can manage appointments from your dashboard.</p>
                    </div>

                    <div className="faq-item">
                        <h4>How can I update my profile?</h4>
                        <p>Open your profile and click Edit Profile.</p>
                    </div>

                    <div className="faq-item">
                        <h4>Medical Emergency</h4>
                        <p>
                            If this is a medical emergency, immediately call
                            <strong> 112 </strong>
                            instead of waiting for an online appointment.
                        </p>
                    </div>

                </div>

            </div>

        </div>
    );
}

export default ContactPage;