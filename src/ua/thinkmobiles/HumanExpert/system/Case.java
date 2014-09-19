package ua.thinkmobiles.HumanExpert.system;

import java.util.List;

public class Case {

    public static final String TAG = "case";
    public static final String TEXT_TAG = "text";
    public static final String IMAGE_URL_TAG = "image";
    public static final String ID_TAG = "id";
    public static final String ANSWERS_TAG = "answers";

    private String text;
    private String imageUrl;
    private int id;
    private List<Answer> answers;

    public Case(String text, String imageUrl, int id, List<Answer> answers) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.id = id;
        this.answers = answers;
    }

    public Case() {}

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
