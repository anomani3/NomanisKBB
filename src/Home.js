import React from "react";
import { useNavigate } from "react-router-dom";
import "./Home.css";

export default function Home() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <div className="animated-bg"></div>

      <div className="content">
        <div className="logo">NOMANI's KABAB</div>
        <h1 className="gradient-text">Experience Multi Cuisine</h1>
        <p className="animated-text">
          Welcome to NOMANI's KABAB, where the aroma of charcoal-grilled perfection fills the air and authentic flavors tell a story of culinary passion. We specialize in serving mouth-watering, succulent kebabs—from classic Seekh to our signature Afghan Chapli—prepared using time-honored recipes and the freshest locally sourced ingredients. Whether you're dining in our vibrant, casual atmosphere or ordering a convenient takeaway meal, we invite you to experience the rich, diverse tastes of Middle Eastern and Indian cuisine.
        </p>

        <div className="btn-group">
          <button className="btn-primary" onClick={() => navigate("/login")}>Sign In</button>
          <button className="btn-secondary" onClick={() => navigate("/signup")}>Sign Up</button>
        </div>
      </div>
    </div>
  );
}
