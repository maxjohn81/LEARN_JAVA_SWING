package javaswing.gui;

import javaswing.model.Produit;
import javaswing.service.ProduitService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProduitPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private ProduitService service = new ProduitService();

    public ProduitPanel() {
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Description", "Prix", "Quantité"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel boutons CRUD
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Ajouter");
        JButton editBtn = new JButton("Modifier");
        JButton deleteBtn = new JButton("Supprimer");
        JButton refreshBtn = new JButton("Rafraîchir");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        addBtn.addActionListener(e -> showProduitDialog(null));
        editBtn.addActionListener(e -> editSelectedProduit());
        deleteBtn.addActionListener(e -> deleteSelectedProduit());
        refreshBtn.addActionListener(e -> loadProduits());

        loadProduits();
    }

    private void loadProduits() {
        List<Produit> produits = service.getAllProduits();
        tableModel.setRowCount(0);
        for (Produit p : produits) {
            tableModel.addRow(new Object[]{p.getId(), p.getNom(), p.getDescription(), p.getPrix(), p.getQuantite()});
        }
    }

    private void showProduitDialog(Produit produit) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Produit", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        JTextField nomField = new JTextField();
        JTextField descField = new JTextField();
        JTextField prixField = new JTextField();
        JTextField quantiteField = new JTextField();

        if(produit != null) {
            nomField.setText(produit.getNom());
            descField.setText(produit.getDescription());
            prixField.setText(String.valueOf(produit.getPrix()));
            quantiteField.setText(String.valueOf(produit.getQuantite()));
        }

        dialog.add(new JLabel("Nom:")); dialog.add(nomField);
        dialog.add(new JLabel("Description:")); dialog.add(descField);
        dialog.add(new JLabel("Prix:")); dialog.add(prixField);
        dialog.add(new JLabel("Quantité:")); dialog.add(quantiteField);

        JButton saveBtn = new JButton("Enregistrer");
        JButton cancelBtn = new JButton("Annuler");

        dialog.add(saveBtn); dialog.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String desc = descField.getText().trim();
            double prix;
            int quantite;

            try {
                prix = Double.parseDouble(prixField.getText().trim());
                quantite = Integer.parseInt(quantiteField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Prix ou quantité invalide !");
                return;
            }

            if(nom.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Le nom est obligatoire !");
                return;
            }

            if(produit == null) {
                service.addProduit(new Produit(0, nom, desc, prix, quantite));
            } else {
                produit.setNom(nom);
                produit.setDescription(desc);
                produit.setPrix(prix);
                produit.setQuantite(quantite);
                service.updateProduit(produit);
            }

            loadProduits();
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void editSelectedProduit() {
        int row = table.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un produit !");
            return;
        }

        int id = (int)tableModel.getValueAt(row,0);
        String nom = (String)tableModel.getValueAt(row,1);
        String desc = (String)tableModel.getValueAt(row,2);
        double prix = (double)tableModel.getValueAt(row,3);
        int quantite = (int)tableModel.getValueAt(row,4);

        Produit produit = new Produit(id, nom, desc, prix, quantite);
        showProduitDialog(produit);
    }

    private void deleteSelectedProduit() {
        int row = table.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un produit !");
            return;
        }

        int id = (int)tableModel.getValueAt(row,0);
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce produit ?", "Confirmer", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            service.deleteProduit(id);
            loadProduits();
        }
    }
}