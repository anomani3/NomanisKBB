import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "./authService";
import Message from "./Message";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("success");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const result = await loginUser(email, password);
      if (result === "Login Successful!") {
        setMessageType("success");
        setMessage(result);
        setTimeout(() => navigate("/"), 2000); // redirect after 2s
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
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required />
        <button type="submit">Login</button>
      </form>

      <Message 
        message={message} 
        type={messageType} 
        onClose={() => setMessage("")} 
      />
    </div>
  );
}

export default Login;
