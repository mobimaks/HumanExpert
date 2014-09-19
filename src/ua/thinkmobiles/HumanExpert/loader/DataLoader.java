package ua.thinkmobiles.HumanExpert.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import ua.thinkmobiles.HumanExpert.json.JSONParser;
import ua.thinkmobiles.HumanExpert.net.HandleHTTP;
import ua.thinkmobiles.HumanExpert.system.LoaderTypes;

public class DataLoader extends AsyncTaskLoader<Object> {

    public final static String URL = "url";
    private String url;
    private LoaderTypes type;
    private Object data;

    public DataLoader(Context context, Bundle args, LoaderTypes type) {
        super(context);
        this.url = args.getString(URL);
        this.type = type;
    }

    @Override
    public Object loadInBackground() {
        HandleHTTP handleHTTP = HandleHTTP.getInstance();
        String json = handleHTTP.getRequest(url);
        JSONParser jsonParser = JSONParser.getInstance();
        if (type == LoaderTypes.SCENARIOS){
             return jsonParser.parseScenarios(json);
        } else if (type == LoaderTypes.CASES){
            return jsonParser.parseCases(json);
        }
        return null;
    }

    @Override
    public void deliverResult(Object data) {
        if (isReset()){
            return;
        }
        this.data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (null == data){
            forceLoad();
        } else {
            deliverResult(data);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        cancelLoad();
    }
}
