package ua.thinkmobiles.HumanExpert.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import ua.thinkmobiles.HumanExpert.R;
import ua.thinkmobiles.HumanExpert.loader.DataLoader;
import ua.thinkmobiles.HumanExpert.system.Answer;
import ua.thinkmobiles.HumanExpert.system.Case;
import ua.thinkmobiles.HumanExpert.system.LoaderTypes;

import java.util.List;

public class CaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object>, OnClickListener {

    public static final String SCENARIO_ID = "scenario_id";

    private final int LOADER_ID = 1;
    private String url;
    private boolean empty;
    private ImageView ivCaseImage;
    private TextView tvCaseText;
    private View btnPanel;
    private Button btn1, btn2, btnBack;
    private Bundle loaderArgs;
    private OnCaseAnswer listener;
    private View progressBar;

    public interface OnCaseAnswer{
        void onCaseAnswer(int caseId);
        void onCaseBack();
        void onLastCase();
    }

    public static CaseFragment newInstance(int caseId){
        CaseFragment caseFragment = new CaseFragment();
        Bundle args = new Bundle();
        args.putInt(SCENARIO_ID, caseId);
        caseFragment.setArguments(args);
        return caseFragment;
    }

    public static CaseFragment newInstance(){
        return newInstance(-1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args){
            int caseId = args.getInt(SCENARIO_ID);
            empty = (caseId == -1);
            if (caseId != -1){
                this.url = String.format(getString(R.string.caseUrl), caseId);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);

        ivCaseImage = (ImageView) view.findViewById(R.id.ivCaseImage);
        tvCaseText = (TextView) view.findViewById(R.id.tvCaseText);
        btnPanel = view.findViewById(R.id.btnPanel);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        progressBar = view.findViewById(R.id.empty_case);

        if (!empty){
            btn1.setEnabled(true);
            btn2.setEnabled(true);
        }

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener = (OnCaseAnswer)getActivity();
        if (!empty){
            loaderArgs = new Bundle();
            loaderArgs.putString(DataLoader.URL, this.url);
            getLoaderManager().initLoader(LOADER_ID, loaderArgs, this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.btnBack){
            listener.onCaseAnswer(Integer.parseInt(v.getTag().toString()));
        } else {
            listener.onCaseBack();
        }
    }

    public void setButtonsEnable(boolean enabled){
        if (btn1 != null && btn2 != null){
            btn1.setEnabled(enabled);
            btn2.setEnabled(enabled);
        }
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        setButtonsEnable(false);
        return new DataLoader(getActivity(), args, LoaderTypes.CASES);
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        Case aCase = (Case) data;
        Picasso.with(getActivity())
               .load(aCase.getImageUrl())
               .error(R.drawable.no_image)
               .placeholder(R.drawable.no_image)
               .into(ivCaseImage);
        tvCaseText.setText(aCase.getText());
        List<Answer> answers = aCase.getAnswers();
        if (null != answers && !answers.isEmpty()){
            setButtonsEnable(true);
            btn1.setText(answers.get(0).getText());
            btn1.setTag(answers.get(0).getCaseId());

            btn2.setText(answers.get(1).getText());
            btn2.setTag(answers.get(1).getCaseId());
        } else {
            btnPanel.setVisibility(View.INVISIBLE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                btnBack.setVisibility(View.VISIBLE);
            }
            handler.sendEmptyMessage(1);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                listener.onLastCase();
            }
        }
    };

    @Override
    public void onLoaderReset(Loader<Object> loader) {}
}