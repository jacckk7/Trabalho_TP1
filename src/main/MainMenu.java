package main;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainMenu extends JFrame {
    private  JButton startButton; 
    private  JButton rankingButton;

    public MainMenu(){
        super("Testing Buttons");
        setLayout(new BorderLayout());

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/assets/menu_background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        startButton = new JButton("START"); 
        add(startButton); 

        rankingButton = new JButton("RANKING"); 
        add(rankingButton); 

        StartHandler sh = new StartHandler();
        startButton.addActionListener(sh);

        RankingHandler rh = new RankingHandler();
        rankingButton.addActionListener(rh);
        // //refresh )
        // setSize(255,239);
        // setSize(256,240);

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
            //faz alguma coisa   
        }
    }
} 
