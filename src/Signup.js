// src/Signup.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "./authService";
import Message from "./Message";

function Signup() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("success");
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      const result = await registerUser(name, email, password); // authService call
      if (result === "User registered successfully!") {
        setMessageType("success");
        setMessage(result);
        setTimeout(() => navigate("/login"), 2000); // redirect after 2s
      } else {
        setMessageType("error");
        setMessage(result);
      }
    } catch (err) {
      setMessageType("error");
      setMessage("Something went wrong!");
    }
  };

  return (
    <div className="signup-container">
      <h2>Sign Up</h2>
      <form onSubmit={handleSignup}>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Sign Up</button>
      </form>

      {message && (
        <Message
          message={message}
          type={messageType}
          onClose={() => setMessage("")}
        />
      )}
    </div>
  );
}

export default Signup;
