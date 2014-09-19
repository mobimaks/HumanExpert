package ua.thinkmobiles.HumanExpert.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.thinkmobiles.HumanExpert.system.Answer;
import ua.thinkmobiles.HumanExpert.system.Case;
import ua.thinkmobiles.HumanExpert.system.Scenario;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private static JSONParser instance = null;

    private JSONParser(){}

    public static JSONParser getInstance(){
        if (null == instance)
            instance = new JSONParser();
        return instance;
    }

    public ArrayList<Scenario> parseScenarios(String json){
        ArrayList<Scenario> scenarios = new ArrayList<>();
        Scenario scenario;
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray(Scenario.TAG);
            JSONObject obj;
            String text;
            int id, caseId;
            for (int i = 0; i < jsonArray.length(); i++){
                obj = jsonArray.getJSONObject(i);
                text = obj.optString(Scenario.TEXT_TAG);
                id = obj.optInt(Scenario.ID_TAG);
                caseId = obj.optInt(Scenario.CASE_ID_TAG);
                scenario = new Scenario(text, id, caseId);
                scenarios.add(scenario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scenarios;
    }

    public Case parseCases(String json){
        Case aCase = new Case();
        try {
            JSONObject caseObj = new JSONObject(json).getJSONObject(Case.TAG);
            String text = caseObj.optString(Case.TEXT_TAG);
            String imageUrl = caseObj.optString(Case.IMAGE_URL_TAG);
            int id = caseObj.optInt(Case.ID_TAG);
            List<Answer> answerList = parseAnswers(caseObj);
            aCase = new Case(text, imageUrl, id, answerList);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return aCase;
    }

    private ArrayList<Answer> parseAnswers(JSONObject caseObj) {
        ArrayList<Answer> answerList = new ArrayList<>();
        try {
            JSONArray answers = caseObj.getJSONArray(Case.ANSWERS_TAG);
            String answerText;
            int answerId, answerCaseId;
            JSONObject answerObj;
            for (int i = 0; i < answers.length(); i++){
                answerObj = answers.getJSONObject(i);
                answerText = answerObj.optString(Answer.TEXT_TAG);
                answerId = answerObj.optInt(Answer.ID_TAG);
                answerCaseId = answerObj.optInt(Answer.CASE_ID_TAG);
                answerList.add(new Answer(answerText, answerId, answerCaseId));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return answerList;
    }
}
