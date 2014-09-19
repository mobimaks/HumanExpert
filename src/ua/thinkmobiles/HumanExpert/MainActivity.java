package ua.thinkmobiles.HumanExpert;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import ua.thinkmobiles.HumanExpert.fragment.CaseFragment;
import ua.thinkmobiles.HumanExpert.fragment.ScenariosFragment;

public class MainActivity extends Activity implements ScenariosFragment.OnScenarioSelect, CaseFragment.OnCaseAnswer {

    private static final String SCENARIOS_FRAGMENT_TAG = "scenarios";
    private static final String CASE_FRAGMENT_TAG = "case";
    private static final String IS_SCENARIO_SELECTED = "is_selected";
    private static final String IS_LAST= "is_last";

    private boolean mDualPane;
    private boolean isScenarioSelected;
    private boolean isLast;
    private ScenariosFragment scenariosFragment;
    private CaseFragment caseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDualPane = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);

        if (null != savedInstanceState){
            isScenarioSelected = savedInstanceState.getBoolean(IS_SCENARIO_SELECTED, false);
            isLast = savedInstanceState.getBoolean(IS_LAST, false);
        }

        scenariosFragment = (ScenariosFragment) getFragmentManager().findFragmentByTag(SCENARIOS_FRAGMENT_TAG);
        caseFragment = (CaseFragment) getFragmentManager().findFragmentByTag(CASE_FRAGMENT_TAG);

        if (mDualPane){
            if (null == scenariosFragment){
                scenariosFragment = new ScenariosFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame1, scenariosFragment, SCENARIOS_FRAGMENT_TAG).commit();
            }
            if (null == caseFragment){
                caseFragment = CaseFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.frame2, caseFragment, CASE_FRAGMENT_TAG).commit();
            }
        } else {
            if (isScenarioSelected){
                showSecond();
            } else {
                showFirst();
                if (null == scenariosFragment){
                    scenariosFragment = new ScenariosFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frame1, scenariosFragment, SCENARIOS_FRAGMENT_TAG).commit();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mDualPane){
            if (isScenarioSelected){
                scenariosFragment.setListEnable(false);
            } else {
                caseFragment.setButtonsEnable(false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_SCENARIO_SELECTED, isScenarioSelected);
        outState.putBoolean(IS_LAST, isLast);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onScenarioSelect(int caseId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (isLast){
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            transaction.remove(caseFragment);
            transaction.replace(R.id.frame2, CaseFragment.newInstance(), CASE_FRAGMENT_TAG).commit();
        }
        caseFragment = CaseFragment.newInstance(caseId);
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame2, caseFragment, CASE_FRAGMENT_TAG).commit();
        if (mDualPane){
            scenariosFragment.setListEnable(false);
        } else {
            showSecond();
        }
        isScenarioSelected = true;
        isLast = false;
    }

    @Override
    public void onCaseAnswer(int caseId) {
        caseFragment = CaseFragment.newInstance(caseId);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame2, caseFragment, CASE_FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void onCaseBack() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.remove(caseFragment);
        transaction.replace(R.id.frame2, CaseFragment.newInstance(), CASE_FRAGMENT_TAG).commit();
        isScenarioSelected = false;
        isLast = false;
        if (!mDualPane){
            showFirst();
        }
    }

    @Override
    public void onLastCase() {
        if (mDualPane){
            scenariosFragment.setListEnable(true);
        }
        isLast = true;
    }

    @Override
    public void onBackPressed() {
        if (mDualPane && isLast && isScenarioSelected){
            onCaseBack();
            return;
        }
        super.onBackPressed();
        isLast = false;
    }

    private void showFirst(){
        findViewById(R.id.frame1).setVisibility(View.VISIBLE);
        findViewById(R.id.frame2).setVisibility(View.GONE);
    }

    private void showSecond(){
        findViewById(R.id.frame1).setVisibility(View.GONE);
        findViewById(R.id.frame2).setVisibility(View.VISIBLE);
    }
}