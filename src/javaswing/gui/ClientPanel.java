package javaswing.gui; // Package dédié aux interfaces graphiques

// Import des classes nécessaires
import javaswing.model.Client;       // Modèle client
import javaswing.service.ClientService; // Service pour gérer la logique métier et la base
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClientPanel extends JPanel {
    private JTable table;                  // Tableau pour afficher les clients
    private DefaultTableModel tableModel;  // Modèle du tableau (données + colonnes)
    private ClientService service = new ClientService(); // Service pour CRUD

    // ========================
    // Constructeur du panel
    // ========================
    public ClientPanel() {
        setLayout(new BorderLayout()); // Layout principal : nord/sud/centre

        // ======== Création du tableau ========
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Email"}, 0); // Colonnes : ID, Nom, Email
        table = new JTable(tableModel); // Création de la JTable
        add(new JScrollPane(table), BorderLayout.CENTER); // Ajout du tableau dans un JScrollPane

        // ======== Panel des boutons CRUD ========
        JPanel buttonPanel = new JPanel(); // Panel pour les boutons
        JButton addBtn = new JButton("Ajouter");
        JButton editBtn = new JButton("Modifier");
        JButton deleteBtn = new JButton("Supprimer");
        JButton refreshBtn = new JButton("Rafraîchir");

        // Ajout des boutons au panel
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, BorderLayout.SOUTH); // Ajout du panel des boutons en bas

        // ======== Actions des boutons ========
        addBtn.addActionListener(e -> showClientDialog(null)); // Ajouter un client
        editBtn.addActionListener(e -> editSelectedClient()); // Modifier client sélectionné
        deleteBtn.addActionListener(e -> deleteSelectedClient()); // Supprimer client sélectionné
        refreshBtn.addActionListener(e -> loadClients()); // Recharger la liste

        // ======== Charger les données au démarrage ========
        loadClients();
    }

    // ========================
    // Charger tous les clients depuis le service
    // ========================
    private void loadClients() {
        List<Client> clients = service.getAllClients(); // Récupère tous les clients depuis la base
        tableModel.setRowCount(0); // Vide le tableau avant de recharger
        for (Client c : clients) {
            tableModel.addRow(new Object[]{c.getId(), c.getNom(), c.getEmail()}); // Ajoute chaque client
        }
    }

    // ========================
    // Afficher un dialogue pour ajouter/modifier un client
    // ========================
    private void showClientDialog(Client client) {
        // Crée une fenêtre modale
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Client", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2, 10, 10)); // Grid : 3 lignes, 2 colonnes, espacement 10px
        dialog.setLocationRelativeTo(this); // Centrer par rapport au panel

        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();

        // Si c'est une modification, remplir les champs avec les valeurs existantes
        if (client != null) {
            nomField.setText(client.getNom());
            emailField.setText(client.getEmail());
        }

        // Ajout des labels et champs
        dialog.add(new JLabel("Nom:"));
        dialog.add(nomField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        // Boutons Enregistrer et Annuler
        JButton saveBtn = new JButton("Enregistrer");
        JButton cancelBtn = new JButton("Annuler");

        dialog.add(saveBtn);
        dialog.add(cancelBtn);

        // Action du bouton Enregistrer
        saveBtn.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String email = emailField.getText().trim();

            // Vérification des champs obligatoires
            if (nom.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !");
                return;
            }

            if (client == null) {
                // Ajouter un nouveau client
                service.addClient(new Client(0, nom, email)); // ID = 0 car auto-increment
            } else {
                // Modifier un client existant
                client.setNom(nom);
                client.setEmail(email);
                service.updateClient(client);
            }

            loadClients(); // Recharger le tableau
            dialog.dispose(); // Fermer la fenêtre
        });

        // Annuler : ferme simplement le dialogue
        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true); // Afficher la fenêtre
    }

    // ========================
    // Modifier le client sélectionné
    // ========================
    private void editSelectedClient() {
        int row = table.getSelectedRow(); // Récupérer la ligne sélectionnée
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un client !");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0); // Récupérer ID
        String nom = (String) tableModel.getValueAt(row, 1); // Récupérer nom
        String email = (String) tableModel.getValueAt(row, 2); // Récupérer email

        Client client = new Client(id, nom, email); // Crée un objet Client
        showClientDialog(client); // Ouvre le dialogue de modification
    }

    // ========================
    // Supprimer le client sélectionné
    // ========================
    private void deleteSelectedClient() {
        int row = table.getSelectedRow(); // Ligne sélectionnée
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un client !");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0); // ID du client
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce client ?", "Confirmer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            service.deleteClient(id); // Supprime via le service
            loadClients(); // Recharger le tableau
        }
    }
}