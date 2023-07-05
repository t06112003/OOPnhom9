/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.game;

/**
 *
 * @author Admin
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class InGame3 extends JFrame implements ActionListener {

    Color background_cl = Color.white;
    Color x_cl = Color.red;
    Color y_cl = Color.blue;
    int column = 10, row = 10, count = 0;
    int xUndo[] = new int[column * row];
    int yUndo[] = new int[column * row];
    boolean tick[][] = new boolean[column + 2][row + 2];
    int Size = 0;
    Container cn;
    JPanel pn, pn2;
    JLabel lb;
    JButton newGame_bt, undo_bt, exit_bt, mode_bt;
    private JButton b[][] = new JButton[column + 2][row + 2];
    Sound music = new Sound();
    Sound se = new Sound();

    public InGame3() {
        super("Game cờ caro");
        cn = this.getContentPane();
        pn = new JPanel();
        pn.setLayout(new GridLayout(column, row));
        for (int i = 0; i <= column + 1; i++) {
            for (int j = 0; j <= row + 1; j++) {
                b[i][j] = new JButton(" ");
                b[i][j].setActionCommand(i + " " + j);
                b[i][j].setBackground(background_cl);
                b[i][j].addActionListener(this);
                tick[i][j] = true;
            }
        }
        for (int i = 1; i <= column; i++) {
            for (int j = 1; j <= row; j++) {
                pn.add(b[i][j]);
            }
        }
        lb = new JLabel("X Đánh Trước");
        newGame_bt = new JButton("New Game");
        undo_bt = new JButton("Undo");
        mode_bt = new JButton("Mode");
        exit_bt = new JButton("Exit");
        newGame_bt.addActionListener(this);
        undo_bt.addActionListener(this);
        mode_bt.addActionListener(this);
        exit_bt.addActionListener(this);
        exit_bt.setForeground(x_cl);
        cn.add(pn);
        pn2 = new JPanel();
        pn2.setLayout(new FlowLayout());
        pn2.add(lb);
        pn2.add(newGame_bt);
        pn2.add(undo_bt);
        pn2.add(mode_bt);
        pn2.add(exit_bt);
        cn.add(pn2, "South");

        this.setVisible(true);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        undo_bt.setEnabled(false);
        playMusic(5);
    }

    public boolean checkWin(int i, int j) {
        int d = 0, k = i, h;
        // kiểm tra hàng
        while (b[k][j].getText() == b[i][j].getText()) {
            d++;
            k++;
        }
        k = i - 1;
        while (b[k][j].getText() == b[i][j].getText()) {
            d++;
            k--;
        }
        if (d > 4) {
            return true;
        }
        d = 0;
        h = j;
        // kiểm tra cột
        while (b[i][h].getText() == b[i][j].getText()) {
            d++;
            h++;
        }
        h = j - 1;
        while (b[i][h].getText() == b[i][j].getText()) {
            d++;
            h--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h++;
            k++;
        }
        h = i - 1;
        k = j - 1;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h--;
            k--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h++;
            k--;
        }
        h = i - 1;
        k = j + 1;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h--;
            k++;
        }
        if (d > 4) {
            return true;
        }
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;
    }

    public void undo() {
        if (Size > 0) {
            b[xUndo[Size - 1]][yUndo[Size - 1]].setText(" ");
            b[xUndo[Size - 1]][yUndo[Size - 1]].setActionCommand(xUndo[Size - 1] + " " + yUndo[Size - 1]);
            b[xUndo[Size - 1]][yUndo[Size - 1]].setBackground(background_cl);
            tick[xUndo[Size - 1]][yUndo[Size - 1]] = true;
            count--;
            if (count % 2 == 0) {
                lb.setText("Lượt Của X");
            } else {
                lb.setText("Lượt Của O");
            }
            Size--;
            if (Size == 0) {
                undo_bt.setEnabled(false);
            }
        }
    }

    public void addPoint(int i, int j) {
        if (Size > 0) {
            b[xUndo[Size - 1]][yUndo[Size - 1]].setBackground(background_cl);
        }
        xUndo[Size] = i;
        yUndo[Size] = j;
        Size++;
        if (count % 2 == 0) {
            b[i][j].setText("X");
            b[i][j].setForeground(x_cl);
            lb.setText("Lượt Của O");
        } else {
            b[i][j].setText("O");
            b[i][j].setForeground(y_cl);
            lb.setText("Lượt Của X");
        }
        tick[i][j] = false;
        count = 1 - count;
        b[i][j].setBackground(Color.GRAY);
        undo_bt.setEnabled(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "New Game") {
            new InGame3().setVisible(true);
            stopMusic();
            playSoundSE(2);
            this.dispose();
        } else if (e.getActionCommand() == "Undo") {
            undo();
        } else if (e.getActionCommand() == "Exit") {
            StartGame jstart = new StartGame();
            jstart.setVisible(true);
            jstart.setLocationRelativeTo(null);
            playSoundSE(4);
            stopMusic();
            this.dispose();
        } else if (e.getActionCommand() == "Mode") {
            GameMode jmode = new GameMode();
            jmode.setVisible(true);
            jmode.setLocationRelativeTo(null);
            stopMusic();
            this.dispose();
        } else {
            String s = e.getActionCommand();
            int k = s.indexOf(32);
            int i = Integer.parseInt(s.substring(0, k));
            int j = Integer.parseInt(s.substring(k + 1, s.length()));
            if (tick[i][j]) {
                addPoint(i, j);
                playSoundSE(1);
            }
            if (checkWin(i, j)) {
                lb.setBackground(Color.MAGENTA);
                JOptionPane.showMessageDialog(this, b[i][j].getText() + " WIN");
                lb.setText(b[i][j].getText() + " WIN");
                for (i = 1; i <= column; i++) {
                    for (j = 1; j <= row; j++) {
                        b[i][j].setEnabled(false);
                    }
                }
                undo_bt.setEnabled(false);
                newGame_bt.setBackground(Color.YELLOW);
                stopMusic();
                playSoundSE(3);
            }
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundSE(int i) {
        se.setFile(i);
        se.play();
    }
}