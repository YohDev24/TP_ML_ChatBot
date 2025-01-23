package app;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Chatbot {
    
    private static final String[] SYMPTOMS = {
        "fatigue", "cough", "high_fever", "breathlessness", "sweating", "malaise"
    };
    private boolean userSick = false;  // Variable pour savoir si l'utilisateur est malade

    /**
     * Génère une réponse en fonction de l'entrée utilisateur.
     * 
     * @param userInput La chaîne de texte saisie par l'utilisateur.
     * @return la réponse du chatbot
     */
    public String getResponse(String userInput) {
        userInput = userInput.toLowerCase();
        
        // Heure actuelle pour personnaliser la réponse
        LocalTime now = LocalTime.now();
        String greeting = "Hello!";

        if (now.isAfter(LocalTime.of(18, 0))) {
            greeting = "Good evening!";
        }

        // Si l'utilisateur répond "yes" ou "no" à la question "Are you sick?"
        if (!userSick && (userInput.contains("yes") || userInput.contains("no"))) {
            if (userInput.contains("yes")) {
                userSick = true;
                return "Got it. Let's collect your symptoms.";
            } else {
                return "Okay. If you need help later, just let me know!";
            }
        }

        // Si l'utilisateur est malade, proposer les symptômes
        if (userSick) {
            return "Let's check your symptoms. Please answer 'yes' or 'no'.";
        }

        // Salutation de départ
        if (userInput.contains("hello") || userInput.contains("hi") || userInput.contains("good morning")) {
            return greeting + " Are you sick?";
        }

        return "Sorry... I don't understand.";
    }
}
