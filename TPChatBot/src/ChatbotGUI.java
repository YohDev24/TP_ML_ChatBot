import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatbotGUI {
    private ChatbotController controller;

    public ChatbotGUI() {
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

        // Panneau pour l'entrée de texte et le bouton
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Champ de texte pour que l'utilisateur tape sa question
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBackground(new Color(255, 255, 255));
        inputField.setForeground(new Color(0, 0, 0));
        panel.add(inputField, BorderLayout.CENTER);

        // Bouton pour envoyer le message
        JButton sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.setBackground(new Color(70, 130, 180)); // Couleur bleue
        sendButton.setForeground(Color.WHITE);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        // Fonctionnalité du bouton "Envoyer"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                if (!userInput.isEmpty()) {
                    // Afficher la question de l'utilisateur dans la zone de texte
                    textArea.append("Vous: " + userInput + "\n");

                    // Obtenir la réponse du chatbot via le contrôleur
                    String botResponse = controller.processUserInput(userInput);

                    // Afficher la réponse du chatbot avec un petit délai pour simuler la réflexion
                    appendTextWithDelay(textArea, "Chatbot: " + botResponse + "\n", 1000);

                    // Effacer le champ de texte
                    inputField.setText("");
                }
            }
        });

        // Permettre l'envoi du message avec la touche Entrée
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    

    // Méthode pour ajouter du texte avec un délai simulé pour "réfléchir"
    private void appendTextWithDelay(JTextArea textArea, String text, int delay) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simuler un délai pour l'animation de réflexion du chatbot
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
                new ChatbotGUI();  // Crée l'interface graphique
            }
        });
    }
}
