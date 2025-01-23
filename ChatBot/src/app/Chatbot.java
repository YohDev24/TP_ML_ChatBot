package app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Chatbot {
    private static final Map<String, String[]> CATEGORIES = new LinkedHashMap<>();
    static {
        CATEGORIES.put("General Symptoms", new String[]{
                "Fever", "Chills", "Weight loss", "Fatigue", "Pain"
        });
        CATEGORIES.put("Respiratory System", new String[]{
            "Cough", "Shortness of breath", "Chest pain", "Phlegm", 
            "Nasal congestion", "Loss of smell", "Wheezing"
        });
        CATEGORIES.put("Digestive System", new String[]{
            "Nausea", "Vomiting", "Diarrhea", "Constipation", 
            "Abdominal pain", "Loss of appetite", "Indigestion", "Heartburn"
        });
        CATEGORIES.put("Skin", new String[]{
            "Itching", "Rash", "Redness", "Paleness", 
            "Bruising", "Swelling", "Blisters"
        });
        CATEGORIES.put("Urinary System", new String[]{
            "Frequent urination", "Burning sensation when urinating", 
            "Blood in urine", "Incontinence"
        });
        CATEGORIES.put("Nervous System", new String[]{
            "Headache", "Dizziness", "Fatigue", "Sleep disturbances", 
            "Numbness", "Tingling", "Confusion"
        });
        CATEGORIES.put("Musculoskeletal System", new String[]{
            "Joint pain", "Muscle pain", "Weakness", "Stiffness", "Swelling"
        });
    }

    private final List<String> collectedSymptoms = new ArrayList<>();
    private List<String> categoryOrder; // Liste des catégories pour l'itération
    private String currentCategory; // Catégorie en cours
    private int categoryIndex = 0; // Index de la catégorie
    private String[] currentSymptoms; // Symptômes de la catégorie actuelle
    private int symptomIndex = 0; // Index du symptôme dans la catégorie actuelle
    private boolean collectingSymptoms = false; // Si on collecte des symptômes pour une catégorie

    /**
     * Lance la collecte des catégories.
     *
     * @return La première question concernant les catégories.
     */
    public String startSymptomCollection() {
        categoryOrder = new ArrayList<>(CATEGORIES.keySet());
        categoryIndex = 0;
        symptomIndex = 0;
        collectedSymptoms.clear();
        currentCategory = null;
        collectingSymptoms = false;

        return "Do you have symptoms related to " + categoryOrder.get(categoryIndex) + "? (yes/no)";
    }

    /**
     * Traite la réponse utilisateur pour la collecte des catégories et symptômes.
     *
     * @param userInput Réponse utilisateur (yes/no).
     * @return La prochaine question ou le résumé des symptômes collectés.
     */
    public String processSymptomResponse(String userInput) {
        userInput = userInput.trim().toLowerCase();

        // Vérifier si la réponse est valide
        if (!userInput.equals("yes") && !userInput.equals("no")) {
            if (collectingSymptoms) {
                return "Please answer 'yes' or 'no'. Do you have " + currentSymptoms[symptomIndex] + "?";
            } else {
                return "Please answer 'yes' or 'no'. Do you have symptoms related to " + categoryOrder.get(categoryIndex) + "?";
            }
        }

        // Si on est en train de collecter des symptômes
        if (collectingSymptoms) {
            if (userInput.equals("yes")) {
                collectedSymptoms.add(currentSymptoms[symptomIndex]);
            }
            symptomIndex++;

            // Passer au prochain symptôme ou retourner à la catégorie
            if (symptomIndex < currentSymptoms.length) {
                return "Do you have " + currentSymptoms[symptomIndex] + "? (yes/no)";
            } else {
                collectingSymptoms = false;
                categoryIndex++;
                return nextCategory();
            }
        }

        // Si on est en train de collecter les catégories
        if (userInput.equals("yes")) {
            currentCategory = categoryOrder.get(categoryIndex);
            currentSymptoms = CATEGORIES.get(currentCategory);
            symptomIndex = 0;
            collectingSymptoms = true;
            return "Do you have " + currentSymptoms[symptomIndex] + "? (yes/no)";
        } else {
            categoryIndex++;
            return nextCategory();
        }
    }

    /**
     * Passe à la prochaine catégorie ou termine la collecte.
     *
     * @return La prochaine question ou un résumé final.
     */
    private String nextCategory() {
        if (categoryIndex < categoryOrder.size()) {
            return "Do you have symptoms related to " + categoryOrder.get(categoryIndex) + "? (yes/no)";
        } else {
            return "You mentioned the following symptoms: " +
                   (collectedSymptoms.isEmpty() ? "none" : String.join(", ", collectedSymptoms));
        }
    }

    /**
     * Vérifie si la collecte des symptômes est en cours.
     *
     * @return true si la collecte est active, false sinon.
     */
    public boolean isCollectingSymptoms() {
        return categoryIndex < categoryOrder.size() || collectingSymptoms;
    }
}
