package app;

import javax.swing.SwingUtilities;

public class ChatbotApp {
	///Lance l'app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatbotUI();  // Initialise l'interface 
            }
        });
    }
}
