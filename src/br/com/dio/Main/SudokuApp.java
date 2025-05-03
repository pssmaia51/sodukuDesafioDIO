package br.com.dio.Main;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardTemplate;

import javax.swing.*;
import java.awt.*;

public class SudokuApp extends JFrame {
    private final Board board;
    private final JTextField[][] cells = new JTextField[9][9];

    public SudokuApp(String dificuldade) {
        board = new Board(BoardTemplate.generateBoard(dificuldade));
        setTitle("Sudoku - " + dificuldade.toUpperCase());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                Space space = board.getSpace().get(i).get(j);
                if (space.isFixed()) {
                    cell.setText(String.valueOf(space.getActual()));
                    cell.setEditable(false);
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setBackground(Color.WHITE);
                }
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        JButton verificarBtn = new JButton("Verificar");
        verificarBtn.addActionListener(e -> verificar());

        JButton limparBtn = new JButton("Limpar Jogadas");
        limparBtn.addActionListener(e -> {
            board.reset();
            atualizarInterface();
        });

        JPanel botoes = new JPanel();
        botoes.add(verificarBtn);
        botoes.add(limparBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void verificar() {
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Space s = board.getSpace().get(i).get(j);
                    if (!s.isFixed()) {
                        String texto = cells[i][j].getText();
                        if (texto.isBlank()) {
                            s.setActual(null);
                        } else {
                            int valor = Integer.parseInt(texto);
                            if (valor >= 1 && valor <= 9) {
                                s.setActual(valor);
                            } else {
                                throw new NumberFormatException();
                            }
                        }
                    }
                }
            }

            if (board.gameIsFinished()) {
                JOptionPane.showMessageDialog(this, "Parabéns! Você completou corretamente!");
            } else if (board.hasErrors()) {
                JOptionPane.showMessageDialog(this, "Existem erros no tabuleiro!");
            } else {
                JOptionPane.showMessageDialog(this, "Ainda faltam espaços para preencher.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida! Use apenas números de 1 a 9.");
        }
    }

    private void atualizarInterface() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Space s = board.getSpace().get(i).get(j);
                if (!s.isFixed()) {
                    cells[i][j].setText("");
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] opcoes = {"Facil", "Intermediario", "Dificil"};
        String escolha = (String) JOptionPane.showInputDialog(
                null,
                "Escolha a dificuldade:",
                "Sudoku",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[1]
        );
        if (escolha != null) {
            SwingUtilities.invokeLater(() -> new SudokuApp(escolha));
        }
    }
}


