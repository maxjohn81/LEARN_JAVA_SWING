package javaswing.service;

import javaswing.db.DatabaseConnection; // Classe utilitaire pour obtenir la connexion
import javaswing.model.Client;          // Modèle client
import java.sql.*;                       // Import pour JDBC (Connection, Statement, ResultSet, SQLException)
import java.util.ArrayList;
import java.util.List;

/**
 * ClientService : gère toutes les opérations CRUD pour les clients
 */
public class ClientService {

    // ========================
    // Récupérer tous les clients
    // ========================
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>(); // Liste pour stocker les clients

        // try-with-resources : auto-ferme Connection, Statement et ResultSet
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {

            // Parcours des résultats et création des objets Client
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),        // id du client
                    rs.getString("nom"),    // nom du client
                    rs.getString("email")   // email du client
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur si quelque chose ne va pas
        }

        return clients; // Retourne la liste des clients
    }

    // ========================
    // Ajouter un client
    // ========================
    public void addClient(Client client) {
        String sql = "INSERT INTO clients(nom, email) VALUES (?, ?)"; // Requête SQL paramétrée
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());   // Remplace le premier ? par le nom
            ps.setString(2, client.getEmail()); // Remplace le deuxième ? par l'email
            ps.executeUpdate();                 // Exécute l'insertion

        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur
        }
    }

    // ========================
    // Mettre à jour un client
    // ========================
    public void updateClient(Client client) {
        String sql = "UPDATE clients SET nom = ?, email = ? WHERE id = ?"; // Requête SQL pour update
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom()); // Remplace le premier ? par le nom
            ps.setString(2, client.getEmail()); // Remplace le deuxième ? par l'email
            ps.setInt(3, client.getId());      // Remplace le troisième ? par l'id
            ps.executeUpdate();                 // Exécute la mise à jour

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // Supprimer un client
    // ========================
    public void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id = ?"; // Requête SQL pour supprimer par id
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);     // Remplace le ? par l'id du client
            ps.executeUpdate();   // Exécute la suppression

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}