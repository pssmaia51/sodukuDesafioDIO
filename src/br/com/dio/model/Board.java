package br.com.dio.model;

import java.util.*;

import static br.com.dio.model.GameStatusEnum.*;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpace() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        boolean anyUserInput = false;
        boolean allFilled = true;

        for (List<Space> row : spaces) {
            for (Space space : row) {
                if (!space.isFixed()) {
                    Integer actual = space.getActual();
                    if (actual == null) {
                        allFilled = false;
                    } else {
                        anyUserInput = true;
                        if (!actual.equals(space.getExpected())) {
                            return INCOMPLETE;
                        }
                    }
                }
            }
        }

        if (!anyUserInput) return NON_STARTED;
        return allFilled ? COMPLETE : INCOMPLETE;
    }

    public boolean changeValue(final int col, final int row, final int value) {
        Space space = spaces.get(row).get(col);
        if (space.isFixed()) return false;
        space.setActual(value);
        return true;
    }

    public boolean cleanValue(final int col, final int row) {
        Space space = spaces.get(row).get(col);
        if (space.isFixed()) return false;
        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(row -> row.forEach(Space::clearSpace));
    }

    public boolean hasErrors() {
        for (int i = 0; i < 9; i++) {
            Set<Integer> rowSet = new HashSet<>();
            Set<Integer> colSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                Integer rowVal = spaces.get(i).get(j).getActual();
                Integer colVal = spaces.get(j).get(i).getActual();

                if (rowVal != null && !rowSet.add(rowVal)) return true;
                if (colVal != null && !colSet.add(colVal)) return true;
            }
        }

        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Set<Integer> block = new HashSet<>();
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        Integer val = spaces.get(boxRow * 3 + r).get(boxCol * 3 + c).getActual();
                        if (val != null && !block.add(val)) return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == COMPLETE;
    }
}






