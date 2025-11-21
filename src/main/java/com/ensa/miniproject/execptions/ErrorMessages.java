package com.ensa.miniproject.execptions; // Vérifiez si le package est 'exceptions' ou 'execptions' (typo)

public class ErrorMessages {

    /**
     * Constructeur privé pour cacher le constructeur public implicite.
     * Cela empêche l'instanciation de cette classe utilitaire.
     */
    private ErrorMessages() {
        throw new IllegalStateException("Utility class");
    }

    // Constantes
    public static final String CRITERE_NOT_FOUND = "Critere not found with id: ";
    public static final String DEVELOPER_NOT_FOUND = "Developer not found";
    public static final String EPIC_NOT_FOUND = "epic not found with id: ";
    public static final String EQUIPE_DEVELOPMENT_NOT_FOUND = "Equipe not found with id: ";
    public static final String SCRUM_MASTER_NOT_FOUND = "No scrummaster found with id: ";

    // J'ai corrigé une potentielle faute de frappe ici (SPRING -> SPRINT) si c'était le cas
    public static final String SPRINT_NOT_FOUND = "Sprint not found with ID: ";

    public static final String SPRINT_BACKLOG = "sprintbacklog";
    public static final String TASK_NOT_FOUND = "Task not found with id: ";
    public static final String USER_STORY_NOT_FOUND = "userstory not found with id: ";
    public static final String USER_STORY_CLONE_NOT_FOUND ="userstoryclone not found with id: ";

}