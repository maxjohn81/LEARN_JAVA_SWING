/*
 * Classe principale qui contient la fenêtre de l'application
 */
package javaswing.gui;

import javax.swing.*; // Import des composants Swing
import java.awt.*;    // Import pour les layouts

/**
 * MainFrame : fenêtre principale de l'application
 */
public class MainFrame extends JFrame {

    // ========================
    // Constructeur
    // ========================
    public MainFrame() {
        super("Gestion Clients"); // Titre de la fenêtre

        setSize(600, 400); // Taille de la fenêtre (largeur x hauteur)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // Fermer l'application lorsque l'utilisateur clique sur la croix

        setLocationRelativeTo(null); 
        // Centre la fenêtre sur l'écran (null = centre de l'écran)

        // ======== Panel principal ========
        // Le panel qui contient le tableau et les boutons CRUD
        ClientPanel clientPanel = new ClientPanel(); 
        add(clientPanel, BorderLayout.CENTER); 
        // Ajoute le panel au centre de la fenêtre (BorderLayout par défaut)

        setVisible(true); // Rend la fenêtre visible
    }
}