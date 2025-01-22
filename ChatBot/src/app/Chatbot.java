package app;

import java.time.LocalTime;

public class Chatbot {
    public String getResponse(String userInput) {
        // Transformer en minuscule pour la cass
        userInput = userInput.toLowerCase();

        // Heure actuelle pour personnaliser la réponse
        LocalTime now = LocalTime.now();
        String greeting = "Bonjour";
        
        if (now.isAfter(LocalTime.of(18, 0))) {
            greeting = "Bonsoir";
        }

        // Réponses flexibles en utilisant "contains" pour des mots-clés
        if (userInput.contains("bonjour") || userInput.contains("salut") || userInput.contains("hey") || userInput.contains("coucou")) {
            return greeting + " ! Comment puis-je t'aider ?";
        }

        //FAIRE LA LOGIQUE ML

        //TODO : a dégager
        return "Désolé, je ne comprends pas bien. Peux-tu reformuler ?";
    }
}
