import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.*;

public class TTT extends JFrame {
    private JButton[][] buttons;
    private boolean xTurn;

    public TTT() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBlackBackground();

        buttons = new JButton[3][3];
        xTurn = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[i][j].addActionListener(new ButtonClickL(i, j));
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    // L is used as Listener for convenience
    private class ButtonClickL implements ActionListener {
        private int row;
        private int col;

        public ButtonClickL(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button.getText().isEmpty()) {
                if (xTurn) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }
                xTurn = !xTurn;
                button.setEnabled(false);

                if (checkWin("X")) {
                    JOptionPane.showMessageDialog(TTT.this, " Congrats ! Player X wins ! ");
                    resetGame();
                } else if (checkWin("O")) {
                    JOptionPane.showMessageDialog(TTT.this, " Congrats ! Player O wins ! ");
                    resetGame();
                } else if (checkDraw()) {
                    JOptionPane.showMessageDialog(TTT.this, "Well Played ! But it's a DRAW ! ");
                    resetGame();
                }
            }
        }
    }

    private boolean checkWin(String player) {
        String symbol = player.equals("X") ? "X" : "O";

        // Check for rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol)
                    && buttons[i][1].getText().equals(symbol)
                    && buttons[i][2].getText().equals(symbol)) {
                return true;
            }
        }

        // Check for columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(symbol)
                    && buttons[1][j].getText().equals(symbol)
                    && buttons[2][j].getText().equals(symbol)) {
                return true;
            }
        }

        // Check for diagonals
        if (buttons[0][0].getText().equals(symbol)
                && buttons[1][1].getText().equals(symbol)
                && buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().equals(symbol)
                && buttons[1][1].getText().equals(symbol)
                && buttons[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
        xTurn = true;
    }

    private void setBlackBackground() {
        Color darkGray = new Color(64, 64, 64);
        UIManager.put("Panel.background", darkGray);
        UIManager.put("Button.background", darkGray);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TTT::new);
    }
}
