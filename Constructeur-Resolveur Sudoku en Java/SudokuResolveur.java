import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class GrilleSudoku {
    private int[][] grille;

    public GrilleSudoku() {
        this.grille = new int[9][9];
    }

    public GrilleSudoku(int[][] grilleInitiale) {
        this.grille = grilleInitiale;
    }

    public int[][] getGrille() {
        return grille;
    }

    public boolean estPlacementValide(int ligne, int colonne, int nombre) {
        for (int i = 0; i < 9; i++) {
            if (grille[ligne][i] == nombre || grille[i][colonne] == nombre) {
                return false;
            }
        }
        int carreLigne = ligne - ligne % 3;
        int carreColonne = colonne - colonne % 3;
        for (int i = carreLigne; i < carreLigne + 3; i++) {
            for (int j = carreColonne; j < carreColonne + 3; j++) {
                if (grille[i][j] == nombre) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean resoudre() {
        for (int ligne = 0; ligne < 9; ligne++) {
            for (int colonne = 0; colonne < 9; colonne++) {
                if (grille[ligne][colonne] == 0) {
                    for (int nombre = 1; nombre <= 9; nombre++) {
                        if (estPlacementValide(ligne, colonne, nombre)) {
                            grille[ligne][colonne] = nombre;
                            if (resoudre()) {
                                return true;
                            } else {
                                grille[ligne][colonne] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

public class SudokuResolveur {
    private static GrilleSudoku grilleSudoku;
    private static JTextField[][] cellulesGrille;
    private static JComboBox<String> comboModeResol;
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Résolveur Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel panneauGrille = new JPanel(new GridLayout(9, 9));
        cellulesGrille = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField(1);
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBorder(BorderFactory.createMatteBorder(
                        i % 3 == 0 ? 1 : 0, j % 3 == 0 ? 1 : 0, 1, 1, Color.BLACK));
                cellulesGrille[i][j] = textField;
                panneauGrille.add(textField);
            }
        }
        frame.add(panneauGrille, BorderLayout.CENTER);

        JMenuBar barreMenu = new JMenuBar();
        JMenu menuFichier = new JMenu("Menu");
        JMenuItem itemRetour = new JMenuItem("Retour au Menu");
        itemRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuSudoku.main(null);
                frame.dispose();
            }
        });
        menuFichier.add(itemRetour);
        barreMenu.add(menuFichier);
        frame.setJMenuBar(barreMenu);

        JPanel panneauHaut = new JPanel(new GridLayout(1, 3));
        frame.add(panneauHaut, BorderLayout.NORTH);

        JButton boutonCharger = new JButton("Charger");
        boutonCharger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chargerGrilleDepuisFichier();
            }
        });
        panneauHaut.add(boutonCharger);

        comboModeResol = new JComboBox<>(new String[]{"Automatique", "Manuel"});
        panneauHaut.add(comboModeResol);

        JButton boutonResoudre = new JButton("Résoudre");
        boutonResoudre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resoudreGrille();
            }
        });
        panneauHaut.add(boutonResoudre);

        frame.setVisible(true);
    }

    private static void chargerGrilleDepuisFichier() {
        JFileChooser selecteurFichier = new JFileChooser();
        selecteurFichier.setDialogTitle("Choisir un fichier de grille");
        selecteurFichier.setFileFilter(new FileNameExtensionFilter("Fichiers de grille Sudoku (*.gri)", "gri"));
        int choixUtilisateur = selecteurFichier.showOpenDialog(null);
        if (choixUtilisateur == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fichier = new FileInputStream(selecteurFichier.getSelectedFile());
                DataInputStream dataStream = new DataInputStream(fichier);
                int[][] grilleInitiale = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    int valeurLigne = dataStream.readInt();
                    for (int j = 8; j >= 0; j--) {
                        int valeurCellule = valeurLigne % 10;
                        valeurLigne /= 10;
                        grilleInitiale[i][j] = valeurCellule;
                        cellulesGrille[i][j].setText(valeurCellule == 0 ? "" : String.valueOf(valeurCellule));
                        cellulesGrille[i][j].setEditable(valeurCellule == 0);
                    }
                }
                dataStream.close();
                grilleSudoku = new GrilleSudoku(grilleInitiale);
            } catch (IOException | NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void resoudreGrille() {
        String modeSelectionne = (String) comboModeResol.getSelectedItem();
        if (modeSelectionne.equals("Automatique")) {
            if (grilleSudoku != null && grilleSudoku.resoudre()) {
                afficherGrille();
            }
        } else if (modeSelectionne.equals("Manuel")) {
            verifierGrille();
        }
    }

    private static void verifierGrille() {
        if (grilleSudoku != null) {
            if (grilleSudokuEstValide()) {
                JOptionPane.showMessageDialog(frame, "Félicitations ! Vous avez résolu le Sudoku !");
            } else {
                JOptionPane.showMessageDialog(frame, "Il y a des erreurs dans la grille. Veuillez corriger !");
            }
        }
    }

    private static boolean grilleSudokuEstValide() {
        if (grilleSudoku == null) {
            return false;
        }

        int[][] grille = grilleSudoku.getGrille();

        // Vérifier les lignes
        for (int i = 0; i < 9; i++) {
            if (!estLigneValide(grille, i)) {
                return false;
            }
        }

        // Vérifier les colonnes
        for (int j = 0; j < 9; j++) {
            if (!estColonneValide(grille, j)) {
                return false;
            }
        }

        // Vérifier les carrés
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!estCarreValide(grille, i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean estLigneValide(int[][] grille, int ligne) {
        boolean[] chiffresUtilises = new boolean[10];
        for (int j = 0; j < 9; j++) {
            int chiffre = grille[ligne][j];
            if (chiffre != 0) {
                if (chiffresUtilises[chiffre]) {
                    return false;
                }
                chiffresUtilises[chiffre] = true;
            }
        }
        return true;
    }

    private static boolean estColonneValide(int[][] grille, int colonne) {
        boolean[] chiffresUtilises = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int chiffre = grille[i][colonne];
            if (chiffre != 0) {
                if (chiffresUtilises[chiffre]) {
                    return false;
                }
                chiffresUtilises[chiffre] = true;
            }
        }
        return true;
    }

    private static boolean estCarreValide(int[][] grille, int ligneDebut, int colonneDebut) {
        boolean[] chiffresUtilises = new boolean[10];
        for (int i = ligneDebut; i < ligneDebut + 3; i++) {
            for (int j = colonneDebut; j < colonneDebut + 3; j++) {
                int chiffre = grille[i][j];
                if (chiffre != 0) {
                    if (chiffresUtilises[chiffre]) {
                        return false;
                    }
                    chiffresUtilises[chiffre] = true;
                }
            }
        }
        return true;
    }

    private static void afficherGrille() {
        if (grilleSudoku != null) {
            int[][] grilleResolue = grilleSudoku.getGrille();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    cellulesGrille[i][j].setText(String.valueOf(grilleResolue[i][j]));
                }
            }
        }
    }
}
