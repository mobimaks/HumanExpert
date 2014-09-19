package ua.thinkmobiles.HumanExpert.system;

public class Scenario {

    public static final String TAG = "scenarios";
    public static final String TEXT_TAG = "text";
    public static final String ID_TAG = "id";
    public static final String CASE_ID_TAG = "caseId";

    private String text;
    private int id;
    private int caseId;

    public Scenario(String text, int id, int caseId) {
        this.text = text;
        this.id = id;
        this.caseId = caseId;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public int getCaseId() {
        return caseId;
    }
}
