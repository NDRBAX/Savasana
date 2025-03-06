<div align="center" id="top"> 
  <img src="./assets/savasana_banner.png" alt="Savasana" height="150px" />
</div>

<h1 align="center">Savasana</h1>

<p align="center">
  <img alt="Github top language" src="https://img.shields.io/github/languages/top/NDRBAX/Savasana?color=56BEB8">
  <img alt="Github language count" src="https://img.shields.io/github/languages/count/NDRBAX/Savasana?color=56BEB8">
  <img alt="Repository size" src="https://img.shields.io/github/repo-size/NDRBAX/Savasana?color=56BEB8">
</p>

<p align="center">
  <a href="#repository-structure">Repository structure</a> &#xa0; | &#xa0;
  <a href="#features">Features</a> &#xa0; | &#xa0;
  <a href="#technologies">Technologies</a> &#xa0; | &#xa0;
  <a href="#requirements">Requirements</a> &#xa0;
</p>

<br>

Welcome to **Savasana**, a full-stack application designed to manage yoga studio’s session reservations. This application enables administrators to efficiently manage yoga sessions, while allowing users to register and participate effortlessly.

## Repository structure

```
/Savasana app
│── /front    # Angular application
│── /back     # Spring Boot API
│── /resources   # Additional project resources (postman collection, sql script, coverage)
│── README.md    # Main project documentation (you are here)
```

Each section has its own **README file** with specific installation and setup instructions:  

- 📄 **Frontend** (`/frontend/README.md`) – Angular 14 application setup and usage  
- 📄 **Backend** (`/backend/README.md`) – Spring Boot API setup and usage  

---

## Features

- 📅 **Session Management**: Create, edit, and delete yoga sessions.  
- 👥 **User Management**: Register, log in, and book sessions.  
- 🔐 **User Roles**:  
  - **Administrator**: Manages sessions and users.  
  - **User**: Books and cancels yoga sessions.  
- 📊 **Dashboard**: Overview of available sessions.  
- ✅ **Comprehensive Testing**: Ensuring application reliability and performance. 

## Technologies

- **Frontend**: Angular 14, TypeScript  
- **Backend**: Spring Boot, Java 11  
- **Database**: MySQL  
- **Testing**: Jest and Cypress (Frontend), JUnit, SpringBootTest and Mockito (Backend)

## Requirements

Ensure that the following dependencies are installed before running the application:  

- **Java 11**  
- **Node.js 16**  
- **Angular CLI 14**  
- **MySQL** (running on the default port **3306**) 