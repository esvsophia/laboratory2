package org.example;

import org.example.model.Mission;
import org.example.output.Summary;
import org.example.output.SummBase;
import org.example.output.SummExtra;
import org.example.parser.IMissionParser;
import org.example.parser.ParserFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame {
    private JTextArea textArea = new JTextArea();
    private JLabel statusLabel = new JLabel("Готов к работе");

    public GUI() {
        setTitle("Анализатор миссий");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton openBtn = new JButton("Открыть");
        openBtn.addActionListener(e -> {
            FileDialog dialog = new FileDialog(this, "Открыть", FileDialog.LOAD);
            dialog.setVisible(true);
            if (dialog.getFile() != null) {
                loadMission(dialog.getDirectory() + dialog.getFile());
            }
        });

        JButton clearBtn = new JButton("Очистить");
        clearBtn.addActionListener(e -> {
            textArea.setText("");
            statusLabel.setText("Готов к работе");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(openBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(statusLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMission(String filePath) {
        try {
            IMissionParser parser = ParserFactory.getParser(filePath);
            Mission mission = parser.loadMission(filePath);

            Summary summary = new SummBase(mission);
            summary = new SummExtra(summary, mission);

            textArea.setText(summary.getSummary());
            statusLabel.setText("Загружен: " + new File(filePath).getName());
        } catch (Exception e) {
            textArea.setText("Ошибка: " + e.getMessage());
        }
    }
}
