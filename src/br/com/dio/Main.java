package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardTemplate;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Board board;

    public static void main(String[] args) {
        System.out.println("=== SUDOKU INTERATIVO ===");
        System.out.print("Escolha o nível (Facil / Intermediario / Dificil): ");
        String nivel = scanner.nextLine();

        board = new Board(BoardTemplate.generateBoard(nivel));

        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Mostrar tabuleiro");
            System.out.println("2. Alterar valor");
            System.out.println("3. Limpar célula");
            System.out.println("4. Verificar status do jogo");
            System.out.println("5. Reiniciar jogo");
            System.out.println("6. Verificar se o jogo foi finalizado");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> mostrarTabuleiro();
                case 2 -> alterarValor();
                case 3 -> limparCelula();
                case 4 -> System.out.println("Status atual: " + board.getStatus());
                case 5 -> {
                    board.reset();
                    System.out.println("Jogo reiniciado!");
                }
                case 6 -> System.out.println("Finalizado: " + board.gameIsFinished());
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void mostrarTabuleiro() {
        List<List<Space>> grid = board.getSpace();
        System.out.println("\nTabuleiro:");
        for (List<Space> row : grid) {
            for (Space space : row) {
                String val = space.getActual() == null ? "_" : String.valueOf(space.getActual());
                System.out.print(space.isFixed() ? "[" + val + "] " : " " + val + "  ");
            }
            System.out.println();
        }
    }

    private static void alterarValor() {
        System.out.print("Informe a linha (0 a 8): ");
        int row = scanner.nextInt();
        System.out.print("Informe a coluna (0 a 8): ");
        int col = scanner.nextInt();
        System.out.print("Informe o novo valor (1 a 9): ");
        int valor = scanner.nextInt();

        boolean sucesso = board.changeValue(col, row, valor);
        System.out.println(sucesso ? "Valor alterado com sucesso." : "Não foi possível alterar (fixo ou inválido).");
    }

    private static void limparCelula() {
        System.out.print("Informe a linha (0 a 8): ");
        int row = scanner.nextInt();
        System.out.print("Informe a coluna (0 a 8): ");
        int col = scanner.nextInt();

        boolean sucesso = board.cleanValue(col, row);
        System.out.println(sucesso ? "Célula limpa." : "Célula fixa, não pode ser limpa.");
    }
}
