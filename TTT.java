import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.*;

public class TTT extends JFrame {
    private JButton[][] btn;
    private boolean XT;

    /*
     * btn - Button
     * XT - XTurn
     */

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

        btn = new JButton[3][3];
        XT = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j] = new JButton();
                btn[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                btn[i][j].addActionListener(new ButtonClickL(i, j));
                add(btn[i][j]);
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
                if (XT) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }
                XT = !XT;
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
            if (btn[i][0].getText().equals(symbol)
                    && btn[i][1].getText().equals(symbol)
                    && btn[i][2].getText().equals(symbol)) {
                return true;
            }
        }

        // Check for columns
        for (int j = 0; j < 3; j++) {
            if (btn[0][j].getText().equals(symbol)
                    && btn[1][j].getText().equals(symbol)
                    && btn[2][j].getText().equals(symbol)) {
                return true;
            }
        }

        // Check for diagonals
        if (btn[0][0].getText().equals(symbol)
                && btn[1][1].getText().equals(symbol)
                && btn[2][2].getText().equals(symbol)) {
            return true;
        }

        if (btn[0][2].getText().equals(symbol)
                && btn[1][1].getText().equals(symbol)
                && btn[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (btn[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setEnabled(true);
                btn[i][j].setText("");
            }
        }
        XT = true;
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
