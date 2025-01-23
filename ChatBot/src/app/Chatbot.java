package app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Chatbot {
    private static final Map<String, String[]> CATEGORIES = new LinkedHashMap<>();
    static {
        // General Symptoms
        CATEGORIES.put("General Symptoms", new String[]{
            "Fever", "Chills", "Weight loss", "Fatigue", "Pain", 
            "Malaise", "Sweating", "High fever", "Mild fever", 
            "Restlessness", "Lethargy", "Toxic look (typhos)"
        });

        // Respiratory System
        CATEGORIES.put("Upper Respiratory System", new String[]{
            "Nasal congestion", "Loss of smell", "Sinus pressure", 
            "Runny nose", "Throat irritation", "Cough"
        });

        CATEGORIES.put("Lower Respiratory System", new String[]{
            "Shortness of breath", "Chest pain", "Phlegm", 
            "Wheezing", "Rusty sputum", "Blood in sputum", 
            "Mucoid sputum", "Breathlessness"
        });

        // Digestive System
        CATEGORIES.put("Upper Digestive System", new String[]{
            "Nausea", "Vomiting", "Loss of appetite", "Heartburn", 
            "Ulcers on tongue", "Acidity"
        });

        CATEGORIES.put("Lower Digestive System", new String[]{
            "Diarrhea", "Constipation", "Abdominal pain", 
            "Belly pain", "Passage of gases", "Indigestion"
        });

        CATEGORIES.put("Severe Digestive Issues", new String[]{
            "Stomach pain", "Stomach bleeding", "Stomach distention", 
            "Internal itching", "Yellow urine", "Dark urine"
        });

        // Skin
        CATEGORIES.put("Skin General", new String[]{
            "Itching", "Skin rash", "Nodal skin eruptions", 
            "Dischromic patches", "Redness", "Bruising", 
            "Swelling", "Paleness"
        });

        CATEGORIES.put("Specific Skin Issues", new String[]{
            "Blisters", "Red sore around nose", "Yellow crust ooze", 
            "Skin peeling", "Silver like dusting", "Small dents in nails", 
            "Inflammatory nails", "Pus filled pimples", "Blackheads", 
            "Scurring"
        });

        // Urinary System
        CATEGORIES.put("Urinary System", new String[]{
            "Frequent urination", "Burning sensation when urinating", 
            "Blood in urine", "Incontinence", "Spotting urination", 
            "Bladder discomfort", "Foul smell of urine", 
            "Continuous feel of urine", "Yellow urine", "Dark urine"
        });

        // Nervous System
        CATEGORIES.put("Common Neurological Symptoms", new String[]{
            "Headache", "Dizziness", "Fatigue", "Sleep disturbances", 
            "Confusion", "Depression", "Irritability", "Anxiety"
        });

        CATEGORIES.put("Motor and Sensory Issues", new String[]{
            "Numbness", "Tingling", "Loss of balance", 
            "Lack of concentration", "Visual disturbances"
        });

        CATEGORIES.put("Severe Neurological Issues", new String[]{
            "Slurred speech", "Altered sensorium", 
            "Spinning movements", "Unsteadiness"
        });

        // Musculoskeletal System
        CATEGORIES.put("Pain and Stiffness", new String[]{
            "Joint pain", "Back pain", "Neck pain", "Hip joint pain", 
            "Knee pain", "Swelling joints", "Movement stiffness", 
            "Painful walking"
        });

        CATEGORIES.put("Muscle Weakness", new String[]{
            "Muscle pain", "Weakness in limbs", "Swelling", 
            "Muscle wasting", "Muscle weakness"
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
