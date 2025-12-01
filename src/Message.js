// src/Message.js
import React, { useEffect } from "react";
import "./Message.css"; // for styling

function Message({ message, type, onClose }) {
  useEffect(() => {
    const timer = setTimeout(onClose, 3000); // auto-hide after 3s
    return () => clearTimeout(timer);
  }, [message, onClose]);

  return (
    <div className={`message ${type}`}>
      {message}
      <span className="close-btn" onClick={onClose}>
        &times;
      </span>
    </div>
  );
}

export default Message;
