package main;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import ranking.Ranking;

public class MainMenu extends JFrame {
    private JButton startButton;
    private JButton rankingButton;

    public MainMenu() {
        super("Zelda");
        setLayout(new BorderLayout());
        Color backgroundColor = new Color(254, 207, 177);
        Color detailColor = new Color(86, 39, 9);

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/assets/menu_background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 160));

        JPanel auxPanel = new JPanel();
        auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.PAGE_AXIS));
        auxPanel.setBackground(backgroundColor);

        startButton = new JButton("  START  ");
        // startButton.setBorder(new LineBorder(detailColor,1,true));
        startButton.setForeground(detailColor);

        auxPanel.add(startButton);

        rankingButton = new JButton("RANKING");
        // rankingButton.setBorder(new LineBorder(detailColor,1,true));
        // rankingButton.setBackground(backgroundColor);
        rankingButton.setForeground(detailColor);
        auxPanel.add(rankingButton);

        StartHandler sh = new StartHandler();
        startButton.addActionListener(sh);

        RankingHandler rh = new RankingHandler();
        rankingButton.addActionListener(rh);

        add(auxPanel);

    }

    private class StartHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            App game = new App();
            game.start();
            dispose();
        }
    }

    private class RankingHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Ranking r = new Ranking("src/ranking.txt");
            r.createWindow();
            dispose();
        }
    }

}
