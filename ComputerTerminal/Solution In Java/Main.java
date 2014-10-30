
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bilguun
 */
public class Main {

    public enum DefaultCommand {

        CLEAR_SCREEN("^c"),
        MOVE_BEGIN("^h"),
        BEGIN_CUR_LINE("^b"),
        DOWN_ONE_ROW("^d"),
        UP_ONE_ROW("^u"),
        MOVE_LEFT("^l"),
        MOVE_RIGHT("^r"),
        ERASE("^e"),
        INSERT_MODE("^i"),
        OVERWRITE_MODE("^o"),
        DECIMAL_MODE("^DD"),
        CIRCUMFLEX_MODE("^^"),
        DEFAULT("");

        private final String value;

        private DefaultCommand(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public static void main(String[] args) throws IOException {
        Terminal t = new Terminal();

        String[][] value = t.proceedCommands(readFile(args[0]));

        t.print(value);
    }

    public static String readFile(String fileName) throws IOException {
        String sCurrentLine;

        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuffer sb = new StringBuffer();

        while ((sCurrentLine = br.readLine()) != null) {
            sb.append(sCurrentLine);
        }

        return sb.toString();
    }

    public static void putText(String s) // writes string s to the screen
    {
        System.out.println(s);
    }

    // -------------------------------------------------------------
    public static String getString() throws IOException // reads a string from
    // the keyboard input
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    // -------------------------------------------------------------
    public static char getChar() throws IOException // reads a character from
    // the keyboard input
    {
        String s = getString();
        return s.charAt(0);
    }

}

class Terminal {

    private String[][] terminal;
    private static final int maxRow = 9;
    private static final int maxCol = 9;

    public String[][] proceedCommands(String commands) {
        List<String> coms = this.expandCommands(commands);

        return this.writeConsole(coms);
    }

    private List<String> expandCommands(String commands) {
        char[] comArray = commands.toCharArray();

        int i = 0;
        StringBuilder sb = new StringBuilder();
        List<String> commandList = new ArrayList<>();

        while (comArray.length > i) {
            sb.setLength(0);
            if (comArray[i] == 94) {
                sb.append(comArray[i]);

                if (i + 1 <= comArray.length) {
                    if (comArray[i + 1] >= 48 && comArray[i + 1] <= 57) {
                        if (i + 2 <= comArray.length) {
                            sb.append(comArray[i + 1]);
                            sb.append(comArray[i + 2]);
                            i += 3;
                        }
                    } else {
                        sb.append(comArray[i + 1]);
                        i += 2;
                    }
                }
                commandList.add(sb.toString());
            } else {
                commandList.add(String.valueOf(comArray[i]));
                i++;
            }
        }

        return commandList;
    }

    private String[][] writeConsole(List<String> commands) {
        Main.DefaultCommand lastCommand = Main.DefaultCommand.DEFAULT;

        if (terminal == null) {
            terminal = new String[10][10];
            for (int i = 0; i < terminal.length; i++) {
                for (int j = 0; j < terminal[i].length; j++) {
                    terminal[i][j] = "";
                }
            }
        }

        int currRow = 0;
        int currCol = 0;

        for (String command : commands) {

            if (command.equals(Main.DefaultCommand.BEGIN_CUR_LINE.getValue())) {
                currCol = 0;
                lastCommand = Main.DefaultCommand.BEGIN_CUR_LINE;
            } else if (command.equals(Main.DefaultCommand.CLEAR_SCREEN.getValue())) {
                terminal = new String[10][10];
                for (int i = 0; i < terminal.length; i++) {
                    for (int j = 0; j < terminal[i].length; j++) {
                        terminal[i][j] = "";
                    }
                }
                lastCommand = Main.DefaultCommand.CLEAR_SCREEN;
            } else if (command.matches("^[\\u005E][\\d]{2}") && command.length() == 3) {
                lastCommand = Main.DefaultCommand.DECIMAL_MODE;
                char[] numbers = command.toCharArray();

                currRow = Integer.parseInt(String.valueOf(numbers[1]));
                currCol = Integer.parseInt(String.valueOf(numbers[2]));
            } else if (command.matches("^[\\u005E]*")) {
                lastCommand = Main.DefaultCommand.CIRCUMFLEX_MODE;

                terminal[currRow][currCol] = "^";
            } else if (command.equals(Main.DefaultCommand.DOWN_ONE_ROW.getValue())) {
                if (currRow < maxRow) {
                    currRow++;
                }
                lastCommand = Main.DefaultCommand.DOWN_ONE_ROW;
            } else if (command.equals(Main.DefaultCommand.ERASE.getValue())) {
                lastCommand = Main.DefaultCommand.ERASE;
                for (int i = currCol; i <= maxCol; i++) {
                    terminal[currRow][i] = "";
                }
            } else if (command.equals(Main.DefaultCommand.INSERT_MODE.getValue())) {
                lastCommand = Main.DefaultCommand.INSERT_MODE;
            } else if (command.equals(Main.DefaultCommand.MOVE_BEGIN.getValue())) {
                lastCommand = Main.DefaultCommand.MOVE_BEGIN;
                currCol = 0;
                currRow = 0;
            } else if (command.equals(Main.DefaultCommand.MOVE_LEFT.getValue())) {
                lastCommand = Main.DefaultCommand.MOVE_LEFT;
                if (currCol > 0) {
                    currCol--;
                }
            } else if (command.equals(Main.DefaultCommand.MOVE_RIGHT.getValue())) {
                lastCommand = Main.DefaultCommand.MOVE_RIGHT;
                if (currCol < maxCol) {
                    currCol++;
                }
            } else if (command.equals(Main.DefaultCommand.OVERWRITE_MODE.getValue())) {
                lastCommand = Main.DefaultCommand.OVERWRITE_MODE;
            } else if (command.equals(Main.DefaultCommand.UP_ONE_ROW.getValue())) {
                lastCommand = Main.DefaultCommand.UP_ONE_ROW;
                if (currRow > 0) {
                    currRow--;
                }
            } else {
                if (lastCommand.equals(Main.DefaultCommand.OVERWRITE_MODE)) {
                    terminal[currRow][currCol] = command;
                } else if (lastCommand.equals(Main.DefaultCommand.INSERT_MODE)) {
                    terminal[currRow][currCol] = command;

                    if (currCol < maxCol) {
                        currCol++;
                    }
                } else {
                    terminal[currRow][currCol] = command;

                    if (currCol < maxCol) {
                        currCol++;
                    }
                }
            }
        }
        return terminal;
    }

    public void print(String[][] terminal) {
        String s = "";
        List<Object> arguments = new ArrayList<>();

        for (int i = 0; i < terminal.length; i++) {
            for (int j = 0; j < terminal[i].length; j++) {
                s += "%-1s";
                arguments.add(terminal[i][j]);
            }
            s += "\n";
        }

        System.out.printf(s, arguments.toArray());
    }
}
