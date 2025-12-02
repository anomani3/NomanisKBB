# NomanisKBB

📌 Project Documentation — Auth Service + React Frontend ✅ Overview

This project consists of:

Backend: Spring Boot Authentication Service

Frontend: React Login & Signup UI

Communication via REST APIs using Axios

🏗️ Backend Setup (Spring Boot) 1️⃣ Create Spring Boot Project

Use Spring Initializr

Dependencies:

Spring Web

Spring Data JPA

PostgreSQL Driver / MySQL Driver (depending on database)

Spring Boot DevTools

2️⃣ Create User Entity @Entity @Table(name = "users") @Getter @Setter @NoArgsConstructor @AllArgsConstructor public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;

@Column(unique = true)
private String email;

private String password;
}

3️⃣ Create UserRepository @Repository public interface UserRepository extends JpaRepository<User, Long> { Optional findByEmail(String email); }

4️⃣ Create Auth REST APIs @RestController @RequestMapping("/api/auth") @CrossOrigin(origins = "http://localhost:3000") public class AuthController {

private final UserRepository userRepository;

public AuthController(UserRepository userRepository) {
    this.userRepository = userRepository;
}

@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Email already exists!"));
    }

    userRepository.save(user);
    return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
}

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    return userRepository.findByEmail(user.getEmail())
            .map(u -> u.getPassword().equals(user.getPassword())
                    ? ResponseEntity.ok(Map.of("message", "Login Successful!"))
                    : ResponseEntity.status(401).body(Map.of("message", "Wrong password!")))
            .orElse(ResponseEntity.status(404).body(Map.of("message", "User not found!")));
}
}

5️⃣ Enable CORS globally @Configuration public class CorsConfig {

@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/auth/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    };
}
}

🎯 Frontend Setup (React) 1️⃣ Create React App npx create-react-app nomaniskababfrontend cd nomaniskababfrontend npm install axios react-router-dom

2️⃣ Configure Routing (src/App.js) import { BrowserRouter as Router, Routes, Route } from "react-router-dom"; import Home from "./Home"; import Login from "./Login"; import Signup from "./Signup";

export default function App() { return ( <Route path="/" element={} /> <Route path="/login" element={} /> <Route path="/signup" element={} /> ); }

3️⃣ Create Auth Service // src/authService.js import axios from "axios";

const API_URL = "http://localhost:8081/api/auth";

export const registerUser = async (name, email, password) => { try { const response = await axios.post(${API_URL}/register, { name, email, password, }); return response.data.message; } catch { return "Error registering user!"; } };

export const loginUser = async (email, password) => { try { const response = await axios.post(${API_URL}/login, { email, password, }); return response.data.message; } catch { return "Login failed!"; } };

4️⃣ Use APIs in Signup & Login Pages

Frontend calls backend API using:

await registerUser(name, email, password) await loginUser(email, password)

🔗 Connecting Frontend to Backend Configuration Value Backend URL http://localhost:8081

Frontend URL http://localhost:3000

Allowed Origins Set in CORS

💡 IMPORTANT:

Run backend first → port 8081

Then run frontend → port 3000

Axios calls must use same URL as backend running environment

▶️ Run Instructions Backend mvn spring-boot:run

Frontend npm start

🎉 Final Result Feature Status User Signup ✅ Working User Login ✅ Working CORS ✅ Fixed JSON Communication ✅ Working Success/Error UI alerts 🔄 Enhancing (in progress)
