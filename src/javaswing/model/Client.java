/*
 * Modèle de données pour représenter un client
 */
package javaswing.model; // Package dédié aux modèles (objets métier) de ton application

/**
 * Classe Client : représente un client avec son id, son nom et son email
 */
public class Client {

    // Attribut unique pour identifier un client dans la base
    private int id;

    // Nom du client
    private String nom;

    // Adresse email du client
    private String email;

    /**
     * Constructeur pour créer un client avec ses informations
     * @param id identifiant unique du client
     * @param nom nom du client
     * @param email email du client
     */
    public Client(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // ========================
    // Getters : méthodes pour lire les valeurs des attributs
    // ========================

    /**
     * Retourne l'id du client
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'email du client
     */
    public String getEmail() {
        return email;
    }

    // ========================
    // Setters : méthodes pour modifier certaines valeurs
    // ========================

    /**
     * Met à jour le nom du client
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Met à jour l'email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }
}