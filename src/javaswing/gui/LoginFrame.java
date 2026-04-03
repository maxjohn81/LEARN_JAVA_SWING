package javaswing.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import javaswing.db.DatabaseConnection;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        super("Connexion");

        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre

        // ======== Image de fond ========
        ImageIcon bgIcon = new ImageIcon("src/javaswing/images/login_bg.png"); // chemin de ton image
        // Redimensionner l'image pour remplir la fenêtre
        Image img = bgIcon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(img);

        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setLayout(new BorderLayout()); // Permet d'ajouter d'autres composants dessus
        setContentPane(backgroundLabel);

        // ======== Panel principal avec padding ========
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setOpaque(false); // Rendre le panel transparent pour voir l'image de fond
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding autour du formulaire

        JLabel userLabel = new JLabel("Utilisateur:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Mot de passe:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Se connecter");
        JButton cancelBtn = new JButton("Annuler");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(cancelBtn);

        backgroundLabel.add(panel, BorderLayout.CENTER); // Ajouter le formulaire au centre

        // ======== Action du bouton Connexion ========
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (checkLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new HomeFrame(); // Ouvre la page d'accueil
                dispose();       // Ferme le login
            } else {
                JOptionPane.showMessageDialog(this, "Utilisateur ou mot de passe incorrect !");
            }
        });

        // Annuler
        cancelBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // ======== Vérification dans la base ========
    private boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // ⚠️ pour plus de sécurité, stocke les passwords hashés !

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si on trouve un utilisateur
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base !");
            return false;
        }
    }
}