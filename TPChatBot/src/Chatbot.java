import java.time.LocalTime;

public class Chatbot {
    public String getResponse(String userInput) {
        // Transformer la question en minuscule pour rendre la recherche insensible à la casse
        userInput = userInput.toLowerCase();

        // Récupérer l'heure actuelle pour personnaliser la réponse
        LocalTime now = LocalTime.now();
        String greeting = "Bonjour";
        if (now.isAfter(LocalTime.NOON) && now.isBefore(LocalTime.of(18, 0))) {
            greeting = "Bon après-midi";
        } else if (now.isAfter(LocalTime.of(18, 0))) {
            greeting = "Bonsoir";
        }

        // Réponses flexibles en utilisant "contains" pour des mots-clés
        if (userInput.contains("bonjour") || userInput.contains("salut") || userInput.contains("hey") || userInput.contains("coucou")) {
            return greeting + " ! Comment puis-je t'aider ?";
        }

        if (userInput.contains("comment ça va") || userInput.contains("ça va")) {
            return "Je vais bien, merci ! Et toi ?";
        }

        // Répondre si l'utilisateur mentionne son nom
        if (userInput.contains("nom") || userInput.contains("qui es-tu")) {
            return "Je suis un chatbot sans nom. Et toi ?";
        }

        // Répondre si l'utilisateur mentionne la météo ou le temps
        if (userInput.contains("temps") || userInput.contains("météo")) {
            return "Je ne suis pas sûr de la météo exacte, mais il fait généralement beau quelque part !";
        }

        // Répondre si l'utilisateur parle d'émotions
        if (userInput.contains("triste")) {
            return "Désolé de t'entendre dire cela. J'espère que tu te sentiras mieux bientôt.";
        } else if (userInput.contains("heureux") || userInput.contains("content")) {
            return "C'est super que tu te sentes bien ! Continue comme ça !";
        }

        // Réponse pour des questions philosophiques
        if (userInput.contains("qu'est-ce que la vie") || userInput.contains("quelle est la vie")) {
            return "La vie, c'est une aventure à découvrir chaque jour. À toi de lui donner un sens.";
        }

        // Réponse d'au revoir
        if (userInput.contains("au revoir") || userInput.contains("à bientôt") || userInput.contains("bye")) {
            return "Au revoir, à bientôt !";
        }

        // Si aucune correspondance n'est trouvée
        return "Désolé, je ne comprends pas bien. Peux-tu reformuler ?";
    }
}
