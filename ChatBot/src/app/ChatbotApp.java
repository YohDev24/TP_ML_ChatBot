package app;

import javax.swing.SwingUtilities;

public class ChatbotApp {
    public static void main(String[] args) {
        // Démarrage de l'interface graphique
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatbotUI();  // Initialise l'interface utilisateur
            }
        });
    }
}
