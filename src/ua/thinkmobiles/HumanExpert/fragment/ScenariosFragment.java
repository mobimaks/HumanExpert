package ua.thinkmobiles.HumanExpert.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ua.thinkmobiles.HumanExpert.R;
import ua.thinkmobiles.HumanExpert.adapter.ScenariosAdapter;
import ua.thinkmobiles.HumanExpert.loader.DataLoader;
import ua.thinkmobiles.HumanExpert.system.LoaderTypes;
import ua.thinkmobiles.HumanExpert.system.Scenario;

import java.util.ArrayList;

public class ScenariosFragment extends Fragment implements OnItemClickListener, LoaderManager.LoaderCallbacks<Object> {

    private ListView scenariosList;
    private ScenariosAdapter adapter;
    private Bundle loaderArgs;
    private final int LOADER_ID = 1;
    private OnScenarioSelect listener;

    private ArrayList<Scenario> scenarios = new ArrayList<>();

    public interface OnScenarioSelect {
        void onScenarioSelect(int caseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenarios, container, false);
        scenariosList = (ListView) view.findViewById(R.id.lvProblem_list);
        adapter = new ScenariosAdapter(getActivity(), scenarios);
        scenariosList.setEmptyView(view.findViewById(R.id.empty_scenario));
        scenariosList.setAdapter(adapter);
        scenariosList.setOnItemClickListener(this);
        return view;
    }

    public void setListEnable(boolean enabled){
        if (null != scenariosList){
            scenariosList.setEnabled(enabled);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener = (OnScenarioSelect) getActivity();

        loaderArgs = new Bundle();
        loaderArgs.putString(DataLoader.URL, getString(R.string.scenariosUrl));
        getLoaderManager().initLoader(LOADER_ID, loaderArgs, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Scenario s = scenarios.get(position);
        listener.onScenarioSelect(s.getCaseId());
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new DataLoader(getActivity(), args, LoaderTypes.SCENARIOS);
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        this.scenarios = (ArrayList<Scenario>) data;
        adapter.setData((ArrayList<Scenario>) data);
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {}
}