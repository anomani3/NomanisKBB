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

#Swagger UI Link

http://localhost:8081/swagger-ui/index.html#/auth-controller/login

#end points:

http://localhost:8081/api/auth/register

{
  "name": "Ashraf123",
  "email": "ashraf1234@example.com",
  "password": "12345"
}


http://localhost:8081/api/auth/login

{
  "email": "ashraf@example.com",
  "password": "12345"
}


#SECURITY *******

🔐 Security (Spring Security + JWT) — Step-by-step Documentation

Goal: secure your Spring Boot backend with JWTs so only authenticated frontends can access protected endpoints.
Assumes your project root package is com.nomaniskabab.authservice.


0) Overview (what we’ll add)


Add Spring Security + JJWT dependencies


Generate JWT on login


Validate JWT on protected requests using filter


Security config: allow public register + login, protect other APIs


Frontend: store token and send Authorization: Bearer <token> header


Test with Postman & Swagger



1) Add dependencies (pom.xml)
Add these inside <dependencies>:
<!-- Spring Security -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JJWT 0.11.5 -->
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.11.5</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.11.5</version>
  <scope>runtime</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.11.5</version>
  <scope>runtime</scope>
</dependency>

Run:
mvn clean install


2) Configuration values (application.properties)
Add secret and token settings (or use env vars):
# JWT
security.jwt.secret=ChangeThisToAVeryLongRandomStringAtLeast256bits
security.jwt.expiration-ms=3600000     # 1 hour
server.port=8081                        # your backend port

Important: In production use environment variables or a secrets manager. Do NOT hardcode secrets.

3) Update User entity (if not present)
File: src/main/java/com/nomaniskabab/authservice/model/User.java
Make sure it has name, email, password, and optional role:
@Entity
@Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String role = "USER";

  // getters & setters
}


4) Jwt utility class
File: src/main/java/com/nomaniskabab/authservice/security/JwtUtil.java
(Compatible with jjwt 0.11.5)
package com.nomaniskabab.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private long expirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String email) {
        final String tokenEmail = extractEmail(token);
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }
}


5) Jwt Authentication Filter
File: src/main/java/com/nomaniskabab/authservice/security/JwtFilter.java
package com.nomaniskabab.authservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        try {
            String email = jwtUtil.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.isTokenValid(token, email)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // invalid token -> just continue, request will be rejected by security if endpoint protected
        }

        filterChain.doFilter(request, response);
    }
}


6) Security configuration
File: src/main/java/com/nomaniskabab/authservice/config/SecurityConfig.java
package com.nomaniskabab.authservice.config;

import com.nomaniskabab.authservice.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) { this.jwtFilter = jwtFilter; }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                  .anyRequest().authenticated()
          )
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


This config: register & login are public; Swagger docs are public; all other endpoints require a valid JWT.


7) Modify AuthController to return token on login
File: src/main/java/com/nomaniskabab/authservice/controller/AuthController.java
package com.nomaniskabab.authservice.controller;

import com.nomaniskabab.authservice.model.User;
import com.nomaniskabab.authservice.service.UserService;
import com.nomaniskabab.authservice.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String result = userService.register(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        String loginResult = userService.login(request.getEmail(), request.getPassword());
        if (!"Login Successful!".equals(loginResult)) {
            return ResponseEntity.status(401).body(Map.of("message", loginResult));
        }
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Login Successful!", "token", token));
    }
}


8) Update UserService (optional: add BCrypt)
File: src/main/java/com/nomaniskabab/authservice/service/UserService.java
Without hashing (simple):
public String register(String name, String email, String password) {
    if (userRepository.findByEmail(email).isPresent()) return "Email already exists!";
    User u = new User(); u.setName(name); u.setEmail(email); u.setPassword(password);
    userRepository.save(u);
    return "User registered successfully!";
}
public String login(String email, String password) {
    return userRepository.findByEmail(email)
        .map(u -> u.getPassword().equals(password) ? "Login Successful!" : "Wrong Password!")
        .orElse("User not found!");
}

With BCrypt (recommended for production):


Add dependency spring-boot-starter-security already present.


Use BCryptPasswordEncoder bean and call encoder.encode(password) on register and encoder.matches(raw, encoded) on login.



9) Frontend changes (React)


After login, store token:


// after successful login response.data.token
localStorage.setItem("token", response.data.token);



Attach token on each protected request:


const token = localStorage.getItem("token");
axios.get("http://localhost:8081/api/protected", {
  headers: { Authorization: `Bearer ${token}` }
});



On logout: localStorage.removeItem("token").



10) Test with Postman (how-to)
A) Register
POST http://localhost:8081/api/auth/register
Content-Type: application/json
Body:
{ "name":"Ashraf","email":"ashraf@example.com","password":"12345" }

Expect:
{ "message":"User registered successfully!" }

B) Login (get token)
POST http://localhost:8081/api/auth/login
Content-Type: application/json
Body:
{ "email":"ashraf@example.com","password":"12345" }

Expect:
{ "message":"Login Successful!","token":"eyJ..."}

Copy the token.
C) Call protected endpoint
GET http://localhost:8081/api/protected
Headers:
Authorization: Bearer <paste-token-here>



If token valid → 200 OK and response body


If token missing/invalid/expired → 401 Unauthorized



11) How to test in Swagger UI


If Swagger endpoints are public, open Swagger: http://localhost:8081/swagger-ui/index.html


Use Authorize button (top-right) and paste:


Bearer eyJ...yourToken...

Then try protected endpoints — Swagger will send the header.
If you want Swagger itself to require basic authentication popup, remove permitAll for swagger paths in SecurityConfig and enable httpBasic().

12) Troubleshooting & common errors


signature exception / expired token — token expired or secret mismatch. Ensure same secret used for generate & parse.


Content-Type 'application/x-www-form-urlencoded' — frontend must send JSON. Use axios/fetch with Content-Type: application/json.


403 on swagger — if swagger endpoints are not permitted in security config, you'll get 403. Permit them or login first.


Jwt parsing errors (jjwt) — ensure JJWT version 0.11.5 with the parserBuilder usage shown above.


NULL column while adding not-null column — update existing DB rows or drop/recreate table (dev only).


react: Network Error — backend not running, CORS misconfigured, or wrong host/port.



13) Quick checklist (deploy/test)


mvn clean install


Start Spring Boot: mvn spring-boot:run


Confirm register & login work in Postman


Confirm you receive token on login


Test protected endpoint with Authorization header


In frontend, store token and attach to axios requests


Use Swagger Authorize to test protected APIs



***********14) Extra improvements (next steps)


Use BCryptPasswordEncoder to hash passwords on register.


Add UserDetailsService and real GrantedAuthority roles for role-based access.


Add refresh tokens for longer sessions.


Move secret to environment variable / vault.


Add logging & rate-limiting for security.










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


#Security changes in Front end

9) Frontend changes (React)

After login, store token:

// after successful login response.data.token
localStorage.setItem("token", response.data.token);


Attach token on each protected request:

const token = localStorage.getItem("token");
axios.get("http://localhost:8081/api/protected", {
  headers: { Authorization: `Bearer ${token}` }
});


On logout: localStorage.removeItem("token").

▶️ Run Instructions Backend mvn spring-boot:run

Frontend npm start

🎉 Final Result Feature Status User Signup ✅ Working User Login ✅ Working CORS ✅ Fixed JSON Communication ✅ Working Success/Error UI alerts 🔄 Enhancing (in progress)
