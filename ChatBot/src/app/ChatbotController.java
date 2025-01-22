package app;

/**
 * Contrôleur pour l'application Chatbot. 
 * Cette classe agit comme un intermédiaire entre l'interface utilisateur et le chatbot, 
 * en transmettant les entrées de l'utilisateur et en récupérant les réponses.
 */

public class ChatbotController {
    private Chatbot chatbot;
    
    public ChatbotController() {
        this.chatbot = new Chatbot();
    }

    /**
     * Traite l'entrée utilisateur et obtient une réponse du chatbot.
     * 
     * @param userInput => La chaîne de texte saisie par l'utilisateur.
     * @return La réponse générée par le chatbot.
     */

    public String processUserInput(String userInput) {
        return chatbot.getResponse(userInput);
    }
}

