package javaswing.gui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    private JPanel contentPanel; // Panel central pour afficher les modules
    private JLabel backgroundLabel; // Label pour l'image d'accueil

    public HomeFrame() {
        super("Page d'accueil");

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ======== Layout principal ========
        setLayout(new BorderLayout());

        // ======== Menu de navigation ========
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton accueilBtn = new JButton("Accueil"); // Nouveau bouton Accueil
        JButton clientsBtn = new JButton("Gestion Clients");
        JButton produitsBtn = new JButton("Gestion Produits");
        JButton logoutBtn = new JButton("Déconnexion");

        menuPanel.add(accueilBtn);
        menuPanel.add(clientsBtn);
        menuPanel.add(produitsBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.WEST); // Menu à gauche

        // ======== Panel central ========
        contentPanel = new JPanel(new BorderLayout());

        // ======== Ajouter l'image de fond ========
        ImageIcon icon = new ImageIcon("src/javaswing/images/home_bg.png"); // chemin de ton image
        backgroundLabel = new JLabel(icon);
        backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundLabel.setVerticalAlignment(SwingConstants.CENTER);
        contentPanel.add(backgroundLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // ======== Actions des boutons ========
        accueilBtn.addActionListener(e -> showHome());
        clientsBtn.addActionListener(e -> showClientPanel());
        produitsBtn.addActionListener(e -> showProduitPanel());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(); // Retour au login
        });

        setVisible(true);
    }

    // Affiche le panel Client dans le contentPanel
    private void showClientPanel() {
        contentPanel.removeAll();
        contentPanel.add(new ClientPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Affiche le panel Produit dans le contentPanel
    private void showProduitPanel() {
        contentPanel.removeAll();
        contentPanel.add(new ProduitPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Affiche la page d'accueil avec l'image de fond
    private void showHome() {
        contentPanel.removeAll();
        contentPanel.add(backgroundLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}