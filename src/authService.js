// src/authService.js
import axios from "axios";

const API_URL = "http://localhost:8081/api/auth";

/**
 * Register a new user
 * @param {string} name
 * @param {string} email
 * @param {string} password
 * @returns {string} message
 */
export const registerUser = async (name, email, password) => {
  try {
    const response = await axios.post(
      `${API_URL}/register`,
      { name, email, password }, // send JSON
      { headers: { "Content-Type": "application/json" } }
    );

    // If backend sends { message: "..." }, return the string
    return response.data.message || "User registered successfully!";
  } catch (error) {
    // Return backend error message if exists, otherwise default
    if (error.response && error.response.data && error.response.data.message) {
      return error.response.data.message;
    }
    return "Network Error";
  }
};

/**
 * Login an existing user
 * @param {string} email
 * @param {string} password
 * @returns {string} message
 */
export const loginUser = async (email, password) => {
  try {
    const response = await axios.post(
      `${API_URL}/login`,
      { email, password },
      { headers: { "Content-Type": "application/json" } }
    );

    return response.data.message || "Login successful!";
  } catch (error) {
    if (error.response && error.response.data && error.response.data.message) {
      return error.response.data.message;
    }
    return "Network Error";
  }
};
