import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class SudokuBuilder extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField[][] gridCells;

    public SudokuBuilder() {
        setTitle("Constructeur de Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton loadButton = new JButton("Charger");
        JButton backButton = new JButton("Retour au menu");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridCells = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField(1);
                textField.setBorder(BorderFactory.createMatteBorder(
                        i % 3 == 0 ? 1 : 0, j % 3 == 0 ? 1 : 0, 1, 1, Color.BLACK));
                gridCells[i][j] = textField;
                gridPanel.add(textField);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> saveGrid());
        loadButton.addActionListener(e -> loadGrid());
        backButton.addActionListener(e -> {
            dispose();
            MenuSudoku.main(null);
        });

        setSize(600, 600);
        setVisible(true);
    }

    private void saveGrid() {
        if (!isValidGrid()) {
            JOptionPane.showMessageDialog(this, "La grille contient des valeurs invalides !");
            return;
        }

        if (!checkGrid()) {
            JOptionPane.showMessageDialog(this, "La grille contient des doublons dans les lignes ou les colonnes !");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer la grille");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".gri")) {
                fileToSave = new File(filePath + ".gri");
            }

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                outputStream.writeObject(getGrid());
                JOptionPane.showMessageDialog(this, "Grille enregistrée avec succès !");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de la grille !");
            }
        }
    }

    private boolean isValidGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = gridCells[i][j].getText().trim();
                if (!value.isEmpty()) {
                    try {
                        int num = Integer.parseInt(value);
                        if (num < 1 || num > 9) {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkGrid() {
        for (int i = 0; i < 9; i++) {
            boolean[] rowCheck = new boolean[10];
            for (int j = 0; j < 9; j++) {
                String value = gridCells[i][j].getText().trim();
                if (!value.isEmpty()) {
                    try {
                        int num = Integer.parseInt(value);
                        if (rowCheck[num]) {
                            return false;
                        }
                        rowCheck[num] = true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }

        for (int j = 0; j < 9; j++) {
            boolean[] colCheck = new boolean[10];
            for (int i = 0; i < 9; i++) {
                String value = gridCells[i][j].getText().trim();
                if (!value.isEmpty()) {
                    try {
                        int num = Integer.parseInt(value);
                        if (num < 1 || num > 9) {
                            return false;
                        }
                        if (colCheck[num]) {
                            return false;
                        }
                        colCheck[num] = true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void loadGrid() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers de grille", "gri"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
                int[][] loadedGrid = (int[][]) inputStream.readObject();
                setGrid(loadedGrid);
                JOptionPane.showMessageDialog(this, "Grille chargée avec succès !");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la grille !");
            }
        }
    }

    private int[][] getGrid() {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = gridCells[i][j].getText().trim();
                grid[i][j] = value.isEmpty() ? 0 : Integer.parseInt(value);
            }
        }
        return grid;
    }

    private void setGrid(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridCells[i][j].setText(grid[i][j] == 0 ? "" : String.valueOf(grid[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuBuilder builder = new SudokuBuilder();
            builder.setVisible(true);
            for (Frame frame : Frame.getFrames()) {
                if (frame.getTitle().equals("Menu Sudoku")) {
                    frame.dispose();
                }
            }
        });
    }
}
