package ru.geekbrains.lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame {

        public MainWindow() {
        setTitle("MyChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300,300,400,400);
        setLayout(new BorderLayout());
        JPanel jp1 = new JPanel();
        jp1.setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jp1.add(jsp);
        add(jp1, BorderLayout.CENTER);


        JPanel jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        JTextField jtf = new JTextField();
        jtf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jta.append(jtf.getText() + "\n");
                    jtf.setText(null);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jp2.add(jtf, BorderLayout.CENTER);
        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.append(jtf.getText()+"\n");
                jtf.setText("");
            }
        });
        jp2.add(send,BorderLayout.EAST);
        add(jp2, BorderLayout.SOUTH);
        setVisible(true);

    }

}
