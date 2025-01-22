package app;

import java.time.LocalTime;

public class Chatbot {
    public String getResponse(String userInput) {
        // Transformer en minuscule pour la cass
        userInput = userInput.toLowerCase();

        // Heure actuelle pour personnaliser la réponse
        LocalTime now = LocalTime.now();
        String greeting = "Hello !";
        
        if (now.isAfter(LocalTime.of(18, 0))) {
            greeting = "Good evening !";
        }

        // Salutation
        if (userInput.contains("hello") || userInput.contains("hi") || userInput.contains("good morning") ) {
            return greeting + " How can I help you?";
        }

        //FAIRE LA LOGIQUE ML

        //TODO : a dégager
        return "Sorry... I don't understand ";
    }
}
