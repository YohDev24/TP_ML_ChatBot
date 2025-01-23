package app;

public class ChatbotController {
    private Chatbot chatbot;

    public ChatbotController() {
        this.chatbot = new Chatbot();
    }

    /**
     * Initialise la collecte des symptômes.
     * 
     * @return La première question de la collecte des symptômes.
     */
    public String startSymptomCollection() {
        return chatbot.startSymptomCollection();
    }

    /**
     * Traite la réponse utilisateur pour la collecte des symptômes.
     * 
     * @param userInput => La chaîne de texte saisie par l'utilisateur.
     * @return La prochaine question ou le résumé des symptômes.
     */
    public String processSymptomResponse(String userInput) {
        return chatbot.processSymptomResponse(userInput);
    }

    /**
     * Vérifie si la collecte des symptômes est en cours.
     * 
     * @return true si la collecte est active, false sinon.
     */
    public boolean isCollectingSymptoms() {
        return chatbot.isCollectingSymptoms();
    }
}
