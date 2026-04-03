package javaswing.db;  // Définit le package de ce fichier. Ici, il est dans "javaswing.db" pour organiser ton projet.

// Import des classes nécessaires pour gérer la connexion à la base de données et les exceptions SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane; // Pour afficher des boîtes de dialogue à l'utilisateur en cas d'erreur

public class DatabaseConnection { // Classe utilitaire pour gérer la connexion à la base de données

    // URL de connexion à la base MySQL
    // Format : jdbc:mysql://[hôte]:[port]/[nom_base]
    private static final String URL = "jdbc:mysql://localhost:3306/javaswing"; 
    
    // Nom d'utilisateur de la base
    private static final String USER = "root"; 
    
    // Mot de passe de l'utilisateur (vide ici)
    private static final String PASSWORD = ""; 

    // Méthode statique pour obtenir une connexion à la base de données
    public static Connection getConnection() {
        try {
            // Charger le driver JDBC de MySQL
            // Cette étape permet de s'assurer que le driver est disponible pour DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Créer et retourner la connexion à la base de données
            // DriverManager se charge de se connecter avec l'URL, l'utilisateur et le mot de passe
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            // Cette exception se produit si le driver JDBC n'est pas trouvé
            e.printStackTrace(); // Affiche l'erreur dans la console pour le développeur
            JOptionPane.showMessageDialog(null, "Driver JDBC introuvable !"); 
            // Affiche un message à l'utilisateur
            return null; // Retourne null si la connexion échoue

        } catch (SQLException e) {
            // Cette exception se produit si la connexion à la base échoue
            e.printStackTrace(); // Affiche l'erreur complète dans la console
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base !"); 
            // Affiche un message à l'utilisateur
            return null; // Retourne null si la connexion échoue
        }
    }
}