package application.viope.math.combinedapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

public class ConsFirstPage extends Fragment{
    View myView;

    private EquDatabaseHelper mydb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.cons_firstpage, container, false);
        mydb = new EquDatabaseHelper(getContext());
        Button startButton = myView.findViewById(R.id.startBtn);
        Button formButton = myView.findViewById(R.id.formsBtn);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toExercises();
            }
        });

        formButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toFormulaPage();
            }
        });
                return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    //------------------------    METHODS    ----------------------------------

    public void toExercises() {
        if(mydb.ConsGetAllQuestions().isEmpty()){
            Toast.makeText(getContext(),"Baixe perguntas da página de configurações", Toast.LENGTH_LONG).show();
        }
        else {
            Fragment fragment = new ConsExercisePage();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    public void toFormulaPage() {
        Fragment fragment = new ConsFormulaPage();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
}
