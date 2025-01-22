package app;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatbotUI {
    private ChatbotController controller;

    public ChatbotUI() {
        this.controller = new ChatbotController();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Fenêtre principale
        JFrame frame = new JFrame("Chatbot Médical");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(new BorderLayout());

        // Titre et Description
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("MEDICAL CHATBOT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 255, 255)); // bleue
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("Hello! I'm your medical chatbot.\n\nPlease enter your symptoms, and I'll try to help you understand your condition.\nI can provide suggestions for the disease and refer you to specialists.");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(255, 255, 255));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setCaretPosition(0);
        descriptionArea.setMargin(new Insets(10, 20, 10, 10));
        titlePanel.add(descriptionArea, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH);

        // Zone de texte pour la conversation
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(new Color(240, 248, 255));
        textPane.setFont(new Font("Arial", Font.PLAIN, 16));
        textPane.setMargin(new Insets(10, 10, 0, 10));

        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panneau entrée de texte et bouton
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Champ de texte pour la question
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBackground(new Color(255, 255, 255));
        inputField.setForeground(new Color(0, 0, 0));
        panel.add(inputField, BorderLayout.CENTER);

        // Bouton
        JButton sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.setBackground(new Color(70, 130, 180)); // Couleur bleue
        sendButton.setForeground(Color.WHITE);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        // Fonctionnalité "Envoyer"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                if (!userInput.isEmpty()) {
                    // Affiche la question de l'utilisateur
                    appendTextWithStyle(textPane, "You: " + userInput, true);

                    // Efface le champ de texte
                    inputField.setText("");

                    // Simuler un délai avant la réponse
                    new Timer(1000, new ActionListener() { 
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            // Obtient la réponse du chatbot
                            String botResponse = controller.processUserInput(userInput);

                            // Affiche la réponse
                            appendTextWithStyle(textPane, "Chatbot: " + botResponse, false);

                            // Arrête le Timer après exécution
                            ((Timer) evt.getSource()).stop();
                        }
                    }).start();
                }
            }
        });


        // Envoi du message avec Entrée
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });
        
        frame.setVisible(true);
        
        inputField.requestFocusInWindow();
         
    }

    // Ajoute du texte avec alignement (à gauche pour le chatbot, à droite pour l'utilisateur)
    private void appendTextWithStyle(JTextPane textPane, String text, boolean isUser) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();

        // Alignement
        StyleConstants.setAlignment(attrs, isUser ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
        // Couleur du texte
        StyleConstants.setForeground(attrs, isUser ? new Color(0, 81, 163) : Color.BLACK);

        // Appliquer le style et ajouter le texte
        try {
            doc.setParagraphAttributes(doc.getLength(), text.length(), attrs, false);
            doc.insertString(doc.getLength(), text + "\n\n", attrs);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
