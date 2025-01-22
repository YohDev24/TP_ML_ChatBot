package app;

public class ChatbotController {
    private Chatbot chatbot;
    
    public ChatbotController() {
        this.chatbot = new Chatbot();
    }

    // reçoit l'entrée de l'utilisateur et renvoie la réponse du chatbot
    public String processUserInput(String userInput) {
        return chatbot.getResponse(userInput);
    }
}

