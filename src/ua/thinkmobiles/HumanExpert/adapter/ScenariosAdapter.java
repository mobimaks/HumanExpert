package ua.thinkmobiles.HumanExpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ua.thinkmobiles.HumanExpert.R;
import ua.thinkmobiles.HumanExpert.system.Scenario;

import java.util.ArrayList;

public class ScenariosAdapter extends BaseAdapter{

    private Context c;
    private ArrayList<Scenario> data;
    private LayoutInflater inflater;

    public ScenariosAdapter(Context context, ArrayList<Scenario> data){
        this.c = context;
        this.data = data;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Scenario> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Scenario scenario = data.get(position);
        View v = convertView;
        if (null == v){
            ViewHolder viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.scenario_item, parent, false);
            viewHolder.textView = (TextView) v.findViewById(R.id.tvItemText);
            v.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) v.getTag();
        viewHolder.textView.setText(scenario.getText());
        return v;
    }

    private static class ViewHolder{
        public TextView textView;
    }
}
