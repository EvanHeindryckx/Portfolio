import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSudoku {
    private static JFrame menuFrame;

    public static void main(String[] args) {
        menuFrame = new JFrame("Menu Sudoku");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton openSolverButton = new JButton("Résolveur Sudoku");
        openSolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ouvreréso();
            }
        });
        panel.add(openSolverButton);

        JButton openBuilderButton = new JButton("Constructeur Sudoku");
        openBuilderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ouvrebuild();
            }
        });
        panel.add(openBuilderButton);

        menuFrame.add(panel);
        menuFrame.setVisible(true);
    }

    private static void ouvreréso() {
        SudokuResolveur.main(null);
        menuFrame.dispose();
    }

    private static void ouvrebuild() {
        SudokuBuilder.main(null);
        menuFrame.dispose();
    }

    public static JFrame getMenuFrame() {
        return menuFrame;
    }
}
