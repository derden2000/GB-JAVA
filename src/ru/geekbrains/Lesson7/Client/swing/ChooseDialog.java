package ru.geekbrains.Lesson7.Client.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseDialog extends JDialog {
    private JButton btnLogin;
    private JButton btnRegister;
    private boolean isRegistered;

    public ChooseDialog(Frame owner) {
        super(owner, "Выберите действие", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        btnLogin = new JButton("Войти");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRegistered=true;
                dispose();
            }
        });

        btnRegister = new JButton("Зарегистрироваться");
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            isRegistered=false;
            dispose();
            }
        });

        panel.add(btnLogin);
        panel.add(btnRegister);

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(owner);

    }

    public boolean getIsRegistered() {
        return isRegistered;
    }
}
