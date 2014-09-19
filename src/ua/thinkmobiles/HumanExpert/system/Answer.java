package ua.thinkmobiles.HumanExpert.system;

public class Answer {


    public static final String TEXT_TAG = "text";
    public static final String ID_TAG = "id";
    public static final String CASE_ID_TAG = "caseId";

    private String text;
    private int id;
    private int caseId;

    public Answer(String text, int id, int caseId) {
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
