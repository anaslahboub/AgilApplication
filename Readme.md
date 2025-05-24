# README - Mini Projet de Gestion de Projets Agile

## Description du Projet

Ce projet est le backend d'une application de gestion de projets Agile développée avec Spring Boot. Il permet de :

Organiser les fonctionnalités en User Stories et Epics

Gérer les Product Backlog et Sprint Backlog

Suivre l'avancement des sprints

Toutes ces fonctionnalités sont disponibles via des API REST. J'ai également configuré Swagger pour tester facilement les API.

J'ai appliqué dans ce projet les compétences acquises cette année :

Tests unitaires avec JUnit et Mockito

Mapping avec MapStruct

Bonnes pratiques de développement Spring Boot.

Et la chose plus principale que jai pris aucours de cette semestre application des patterns 
   - DI : dependency Injection
   - IoC : inversion of Controle
   - DI : Dependency Injection*
   - microServices
   - clean Controller sans logique metier

- **Gestion du Product Backlog** : Création, modification et suppression des User Stories.
- **Gestion des Epics** : Organisation des User Stories en Epics.
- **Gestion des Sprints** : Création et suivi des Sprint Backlogs.
- **Gestion des Tasks** : Création et suivi des tâches associées aux User Stories.
- **Gestion des Utilisateurs** : Authentification et gestion des rôles (Product Owner, Scrum Master, Développeur).

## Structure du Projet

Le projet est organisé comme suit :

```
miniProject/
├── .idea/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ensa.miniproject/
│   │   │       ├── Aspects/   
│   │   │       ├── config/
│   │   │       │   ├── SwaggerConfig.java
│   │   │       ├── controllers/
│   │   │       │   ├── DeveloperController.java
│   │   │       │   ├── EpicController.java
│   │   │       │   ├── EquipeController.java
│   │   │       │   ├── ProductBacklogController.java
│   │   │       │   ├── ProductOwnerController.java
│   │   │       │   ├── ProjectController.java
│   │   │       │   ├── ScrumMasterController.java
│   │   │       │   ├── SprintBacklogController.java
│   │   │       │   ├── SprintController.java
│   │   │       │   ├── UserStoryCloneController.java
│   │   │       │   └── UserStoryController.java
│   │   │       ├── DTO/
│   │   │       ├── entities/
│   │   │       │   ├── Critere.java
│   │   │       │   ├── Developer.java
│   │   │       │   ├── Epic.java
│   │   │       │   ├── EquipeDevelopment.java
│   │   │       │   ├── Etat.java
│   │   │       │   ├── Priorite.java
│   │   │       │   ├── ProductBacklog.java
│   │   │       │   ├── ProductOwner.java
│   │   │       │   ├── Project.java
│   │   │       │   ├── ScrumMaster.java
│   │   │       │   ├── Sprint.java
│   │   │       │   ├── SprintBacklog.java
│   │   │       │   ├── Status.java
│   │   │       │   ├── Task.java
│   │   │       │   ├── User.java
│   │   │       │   ├── UserStory.java
│   │   │       │   └── UserStoryClone.java
│   │   │       ├── exceptions/
|   |   |       |   ├── GlobalExeptionHandler.java
│   │   │       │   ├── InvalidDateExeption.java
│   │   │       │   ├── ResourceNotFoundExeption.java
│   │   │       ├── mapping/
│   │   │       └── repository/
│   │   └── resources/
│   └── test/
└── pom.xml
```

## Technologies Utilisées

- **Backend** : Spring Boot (Java)
- **Base de données** : MySQL
- **Sécurité** : Spring Security (JWT)
- **API** : RESTful
- **Tests** : JUnit, Mockito

## Entités Principales

1. **UserStory** : Représente une User Story avec ses attributs (titre, description, priorité, statut, etc.).
2. **Epic** : Regroupe plusieurs User Stories.
3. **ProductBacklog** : Contient les User Stories et Epics.
4. **SprintBacklog** : Contient les User Stories et Tasks pour un sprint.
5. **Task** : Tâche associée à une User Story.
6. **User** : Gère les utilisateurs et leurs rôles (Product Owner, Scrum Master, Développeur).

## Installation et Exécution

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/anaslahboub/AgilApplication
   ```

2. **Configurer la base de données** :
   - Modifier le fichier `application.properties` pour configurer la connexion à la base de données.

3. **Lancer l'application** :
   ```bash
   mvn spring-boot:run
   ```

4. **Accéder à l'application** :
   - L'API sera disponible sur `http://localhost:8080`.

## Documentation API

Les endpoints de l'API sont documentés via Swagger. Accédez à la documentation après avoir lancé l'application :
- `http://localhost:8080/swagger-ui.html`

## Auteurs

- AnasLahboub
- Ahmed Abghainouz

## Licence

Ce projet est sous licence [MIT](LICENSE).