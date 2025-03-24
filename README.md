
# Agile Project Management Application

## Overview
This project is a **Spring Boot-based backend application** designed to facilitate Agile project management. It allows teams to manage **User Stories**, **Epics**, **Product Backlogs**, and **Sprint Backlogs** efficiently. The application supports **Product Owners**, **Scrum Masters**, and **Developers** in tracking tasks, prioritizing work, and monitoring progress throughout the project lifecycle.

The application is built with **Spring Boot** and follows RESTful API principles, ensuring scalability, security, and ease of integration with frontend systems or other services.

---

## Key Features
### 1. **Product Backlog Management**
   - Create and manage **Product Backlogs**.
   - Add, modify, and delete **User Stories**.
   - Prioritize **User Stories** based on business value, urgency, complexity, and dependencies.
   - Link **User Stories** to **Epics** for better organization.

### 2. **Epic Management**
   - Create and manage **Epics**.
   - Link **User Stories** to **Epics**.
   - Visualize all **User Stories** associated with an **Epic**.

### 3. **Sprint Backlog Management**
   - Create and manage **Sprints**.
   - Select **User Stories** from the **Product Backlog** to include in the **Sprint Backlog**.
   - Manage **Tasks** associated with each **User Story**.
   - Track the status of **User Stories** and **Tasks** (e.g., *To Do*, *In Progress*, *Done*).

### 4. **User Story Management**
   - Add, modify, and delete **User Stories**.
   - Define **acceptance criteria** for each **User Story**.
   - Track the status of **User Stories** (e.g., *To Do*, *In Progress*, *Done*).

### 5. **Task Management**
   - Create and manage **Tasks** for each **User Story**.
   - Track the status of **Tasks** (e.g., *To Do*, *In Progress*, *Done*).

### 6. **User Management**
   - **Authentication**: User registration and login (JWT-based).
   - **Role-Based Access Control**:
     - **Product Owner**: Manages **Product Backlog** and **Epics**.
     - **Scrum Master**: Manages **Sprints** and tracks progress.
     - **Developer**: Updates **Tasks** and **User Stories**.

### 7. **Reporting and Tracking**
   - Track progress using **Burndown Charts**.
   - View historical data for completed **Sprints**.
   - Generate custom reports for project performance.

---

## Technologies Used
- **Backend Framework**: Spring Boot
- **Programming Language**: Java
- **Database**: MySQL
- **API**: RESTful APIs (Spring MVC)
- **Security**: Spring Security (JWT Authentication)
- **Testing**: JUnit, Mockito
- **Build Tool**: Maven
- **Other Libraries**: Lombok
- **API Documentation**: Swagger

---

## Database Schema
### Key Entities
1. **UserStory**
   - Attributes: `title`, `description`, `priority`, `status`, `epic`, `productBacklog`, `sprintBacklog`
2. **Epic**
   - Attributes: `title`, `description`, `userStories`
3. **ProductBacklog**
   - Attributes: `name`, `epics`, `userStories`
4. **SprintBacklog**
   - Attributes: `name`, `userStories`, `tasks`
5. **Task**
   - Attributes: `title`, `description`, `status`, `userStory`

### Relationships
- A **UserStory** can be linked to one or more **Epics**.
- A **UserStory** belongs to a **ProductBacklog** and can be moved to a **SprintBacklog**.
- A **SprintBacklog** contains multiple **UserStories** and **Tasks**.

---

## Features
- **RESTful APIs**: Exposes endpoints for CRUD operations.
- **Database Integration**: Uses **MySQL** for data persistence.
- **Security**: Implements **JWT-based authentication** and role-based access control.
- **Validation**: Includes input validation for API requests.
- **Error Handling**: Custom error responses for better debugging.
- **Logging**: Integrated logging for monitoring and troubleshooting.

---

## Prerequisites
Before running the project, ensure you have the following installed:
- **Java JDK 21**
- **Maven**
- **MySQL** (installed and running)

---

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/anaslahboub/AgilApplication.git
   cd AgilApplication
   ```

2. **Configure the Database**:
   - Update the `application.properties` or `application.yml` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your-database-name
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the APIs**:
   - The application will start on `http://localhost:8080`.
   - Use tools like **Postman** or **cURL** to interact with the APIs.
   - Access the **Swagger UI** for API documentation at:
     ```
     http://localhost:8080/swagger-ui.html
     ```

---

## Project Structure

![image](https://github.com/user-attachments/assets/643a6983-921c-4f1b-ac3a-b30eb44ac6d7)


---

## API Endpoints
### User Management
- `POST /api/auth/register` - Register a new user.
- `POST /api/auth/login` - Authenticate and generate a JWT token.

### Product Backlog Management
- `GET /api/product-backlogs` - Get all Product Backlogs.
- `POST /api/product-backlogs` - Create a new Product Backlog.
- `PUT /api/product-backlogs/{id}` - Update a Product Backlog.

### User Story Management
- `GET /api/user-stories` - Get all User Stories.
- `POST /api/user-stories` - Create a new User Story.
- `PUT /api/user-stories/{id}` - Update a User Story.

### Sprint Management
- `GET /api/sprints` - Get all Sprints.
- `POST /api/sprints` - Create a new Sprint.
- `PUT /api/sprints/{id}` - Update a Sprint.

---

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes and push to the branch.
4. Submit a pull request.

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Contact
For any questions or feedback, feel free to reach out:
- **Your Name**
- **Email**: your.email@example.com
- **GitHub**: [anaslahboub](https://github.com/anaslahboub)

---

Let me know if you need further adjustments or additional details! 🚀
