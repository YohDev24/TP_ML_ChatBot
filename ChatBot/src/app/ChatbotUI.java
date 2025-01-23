package app;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Initialise et affiche l'interface graphique de l'application. 
     * Configure tous les composants de l'interface, les ajoute à la fenêtre principale et affiche la fenêtre.
     */
    private void createAndShowGUI() {
        // Fenêtre principale
        JFrame frame = createMainFrame();
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/app_icon.jpg"));
        frame.setIconImage(icon.getImage());
        
        // Panneau titre et description
        JPanel titlePanel = createTitlePanel();
        frame.add(titlePanel, BorderLayout.NORTH);

        // Zone de texte pour la conv'
        JTextPane textPane = createTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panneau entrée de texte et bouton
        JPanel panel = createInputPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Fonctionnalité "Envoyer"
        setupSendButton(panel, textPane);

        // Envoi du message avec Entrée
        setupEnterKeyAction(panel);

        // Afficher la fenêtre
        frame.setVisible(true);

        setInitialFocus(panel);
    }

    /**
     * Crée et retourne la fenêtre principale de l'application.
	 * Configurée avec un titre, une taille et est positionné au centre à l'écran.
	 * 
	 * @return JFrame => la fenêtre principale.
     */
    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Chatbot Médical");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centre la fenêtre
        return frame;
    }
    
    /**
     * Crée et retourne un panneau contenant le titre et la description.
	 * Le titre est affiché en haut du panneau avec une description détaillant les fonctionnalités du chatbot.
	 * 
	 * @return JPanel => le panneau du titre et la description. 
     */
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("MEDICAL CHATBOT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 255, 255)); // bleu
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
        
        return titlePanel;
    }
    
    /**
     * Crée et retourne une zone de texte qui affiche la conversation.
     * 
     * @return JTextPane => la zone de texte de la conversation.
     */
    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(new Color(240, 248, 255));
        textPane.setFont(new Font("Arial", Font.PLAIN, 16));
        textPane.setMargin(new Insets(10, 10, 0, 10));
        return textPane;
    }

    /**
     * Crée et retourne un panneau contenant un champ de texte pour la demande de l'utilisateur et le bouton "Envoyer".
     * 
     * @return JPanel => panneau de saisie de texte et du bouton d'envoi.
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBackground(new Color(255, 255, 255));
        inputField.setForeground(new Color(0, 0, 0));
        panel.add(inputField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.setBackground(new Color(70, 130, 180)); // Couleur bleue
        sendButton.setForeground(Color.WHITE);
        panel.add(sendButton, BorderLayout.EAST);

        return panel;
    }

    /**
     * Configure l'action du bouton "Envoyer". Lorsqu'il est cliqué, le texte de l'utilisateur est affiché dans la zone de texte
     * et le chatbot simule un délai avant de répondre.
     */
    private void setupSendButton(JPanel panel, JTextPane textPane) {
        JTextField inputField = (JTextField) panel.getComponent(0);
        JButton sendButton = (JButton) panel.getComponent(1);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText().trim().toLowerCase();
                if (!userInput.isEmpty()) {
                    appendTextWithStyle(textPane, "You: " + userInput, true);
                    inputField.setText("");

                    new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            String botResponse = controller.processUserInput(userInput);

                            appendTextWithStyle(textPane, "Chatbot: " + botResponse, false);

                            // Si l'utilisateur est malade, démarrer la collecte des symptômes
                            if (botResponse.equals("Got it. Let's collect your symptoms.")) {
                                collectSymptoms(textPane, inputField, sendButton);
                            }

                            ((Timer) evt.getSource()).stop();
                        }
                    }).start();
                }
            }
        });
    }



    private void collectSymptoms(JTextPane textPane, JTextField inputField, JButton sendButton) {
        String[] symptoms = {"fatigue", "cough", "high_fever", "breathlessness", "sweating", "malaise"};
        List<String> symptomsList = new ArrayList<>();
        int[] symptomIndex = {0}; // Utilisation d'un tableau pour permettre la modification dans les listeners

        // Afficher la première question
        appendTextWithStyle(textPane, "Chatbot: Do you have " + symptoms[symptomIndex[0]] + "?", false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText().trim().toLowerCase();
                if (!userInput.isEmpty()) {
                    // Afficher la réponse de l'utilisateur
                    appendTextWithStyle(textPane, "You: " + userInput, true);

                    // Vérifier la réponse de l'utilisateur (yes/no)
                    if (userInput.equals("yes")) {
                        symptomsList.add(symptoms[symptomIndex[0]]);
                    }

                    symptomIndex[0]++;
                    inputField.setText("");

                    // Continuer à poser des questions ou terminer
                    if (symptomIndex[0] < symptoms.length) {
                        appendTextWithStyle(textPane, "Chatbot: Do you have " + symptoms[symptomIndex[0]] + "?", false);
                    } else {
                        // Afficher le résumé des symptômes
                        appendTextWithStyle(textPane,
                                "Chatbot: You mentioned the following symptoms: " +
                                (symptomsList.isEmpty() ? "none" : String.join(", ", symptomsList)),
                                false);

                        // Nettoyer les écouteurs pour éviter d'empiler les actions
                        for (ActionListener al : sendButton.getActionListeners()) {
                            sendButton.removeActionListener(al);
                        }
                    }
                }
            }
        });
    }



    /**
     * Configure l'action de la touche "Entrée". Lorsqu'on appuie sur la touche Entrée, le bouton "Envoyer" est déclenché pour envoyer le message
     * 
     * @param panel => panneau contenant le champ de texte et le bouton d'envoi.
     */
    private void setupEnterKeyAction(JPanel panel) {
        JTextField inputField = (JTextField) panel.getComponent(0);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sendButton = (JButton) panel.getComponent(1);
                sendButton.doClick();
            }
        });
    }

    /**
     * Met le focus sur le champ de texte de saisie dès que l'application est lancée. 
     * 
     * @param panel => panneau contenant le champ de texte.
     */

    private void setInitialFocus(JPanel panel) {
        JTextField inputField = (JTextField) panel.getComponent(0);
        inputField.requestFocusInWindow();
    }

    /**
     * Ajoute un texte à la zone de texte avec un style spécifique. 
     * Style : Le texte est aligné et coloré en fonction de si c'est l'utilisateur ou le chatbot.
     * 
     * @param textPane => zone de texte où le texte sera ajouté.
     * @param text => texte à ajouter à la zone de texte.
     * @param isUser => Indique si le texte provient de l'utilisateur (true) ou du chatbot (false).
     */

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

