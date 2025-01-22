package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatbotApp {
    private ChatbotController controller;

    public ChatbotApp() {
        this.controller = new ChatbotController();  
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Fenêtre principale
        JFrame frame = new JFrame("Chatbot Médical");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(new BorderLayout());

        //Titre et Description
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("CHATBOT MEDICAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 255, 255)); // bleue
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("Bonjour ! Je suis votre chatbot médical.\n\nVeuillez entrer vos symptômes, et je vais essayer de vous aider à comprendre votre état de santé.\nJe peux vous fournir des suggestions pour la maladie et vous orienter vers les spécialistes.");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(255, 255, 255));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setCaretPosition(0);
        descriptionArea.setMargin(new Insets(10, 20, 10, 10));
        titlePanel.add(descriptionArea, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH); 

        // Zone de texte pour la conv'
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(240, 248, 255));
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setMargin(new Insets(10, 20, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
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
                    textArea.append("Vous: " + userInput + "\n");

                    // Obtient la réponse du chatbot 
                    String botResponse = controller.processUserInput(userInput);

                    // Affiche la réponse (petit délai pour simuler la réflexion)
                    appendTextWithDelay(textArea, "Chatbot: " + botResponse + "\n", 1000);

                    // Effacer le champ de texte
                    inputField.setText("");
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
    }

    

    // Permet d'ajouter du texte avec un délai simulé 
    private void appendTextWithDelay(JTextArea textArea, String text, int delay) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textArea.append(text);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }

    public static void main(String[] args) {
        // Démarrage de l'interface graphique
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatbotApp();  
            }
        });
    }
}

