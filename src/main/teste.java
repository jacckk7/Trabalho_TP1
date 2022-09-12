package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
class teste extends JFrame
{
    JButton b1;
    JLabel l1;

    public teste()
    {
        setTitle("Background Color for JFrame");
        setSize(256,240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        /*
        One way
        -----------------
        setLayout(new BorderLayout());
        JLabel background=new JLabel(new ImageIcon("C:\\Users\\Computer\\Downloads\\colorful design.png"));
        add(background);
        background.setLayout(new FlowLayout());
        l1=new JLabel("Here is a button");
        b1=new JButton("I am a button");
        background.add(l1);
        background.add(b1);
        */

        // Another way
        setLayout(new BorderLayout());
        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/assets/menu_background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        l1=new JLabel("Here is a button");
        b1=new JButton("I am a button");
        add(l1);
        add(b1);
        // Just for refresh :) Not optional!
        setSize(399,399);
        setSize(256,240);
    }

    public static void main(String args[])
    {
        new teste();
    }
} 