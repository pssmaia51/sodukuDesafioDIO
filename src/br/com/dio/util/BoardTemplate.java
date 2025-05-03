package br.com.dio.util;

import br.com.dio.model.Space;

import java.util.*;

public final class BoardTemplate {

    private BoardTemplate() {}

    public static List<List<Space>> generateBoard(String difficulty) {
        int clues;
        switch (difficulty.toLowerCase()) {
            case "facil" -> clues = 40;
            case "intermediario" -> clues = 32;
            case "dificil" -> clues = 24;
            default -> clues = 32;
        }

        int[][] fullSolution = generateSolvedBoard();
        boolean[][] isClue = generateClueMask(clues);

        List<List<Space>> board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                boolean fixed = isClue[i][j];
                row.add(new Space(fullSolution[i][j], fixed));
            }
            board.add(row);
        }

        return board;
    }

    private static int[][] generateSolvedBoard() {
        int[][] board = new int[9][9];
        solveBoard(board, 0, 0);
        return board;
    }

    private static boolean solveBoard(int[][] board, int row, int col) {
        if (row == 9) return true;
        if (col == 9) return solveBoard(board, row + 1, 0);

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) nums.add(i);
        Collections.shuffle(nums);

        for (int num : nums) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveBoard(board, row, col + 1)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        int startRow = row / 3 * 3, startCol = col / 3 * 3;
        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++)
                if (board[i][j] == num) return false;

        return true;
    }

    private static boolean[][] generateClueMask(int clues) {
        boolean[][] mask = new boolean[9][9];
        Random rand = new Random();
        int placed = 0;
        while (placed < clues) {
            int r = rand.nextInt(9), c = rand.nextInt(9);
            if (!mask[r][c]) {
                mask[r][c] = true;
                placed++;
            }
        }
        return mask;
    }
}
