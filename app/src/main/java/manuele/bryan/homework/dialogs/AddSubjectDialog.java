package manuele.bryan.homework.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import manuele.bryan.homework.adapters.ListAdapterSubjects;
import manuele.bryan.homework.R;
import manuele.bryan.homework.SwipeDismissListViewTouchListener;
import manuele.bryan.homework.databases.DataBaseIO;

public class AddSubjectDialog extends DialogFragment {
    public interface AddSubjectDialogListener {
        void onFinishSubjectAddDialog(String subject);
        void onFinishSubjectDeleteDialog(List<String> subjects, int pos);
    }

    public static final String SUBJECT_BUNDLE_KEY = "subject";

    public AddSubjectDialogListener addSubjectDialogListener;

    DataBaseIO dataBaseIO;

    Context context;
    List<String> subjects;
    ListAdapterSubjects listAdapter;

    ImageView imageView;
    ListView listView;
    EditText editText;
    ImageButton submitButton;

    public static AddSubjectDialog newInstance(ArrayList<String> subjects) {
        AddSubjectDialog addSubjectDialog = new AddSubjectDialog();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SUBJECT_BUNDLE_KEY, subjects);
        addSubjectDialog.setArguments(bundle);

        return addSubjectDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjects = getArguments().getStringArrayList(SUBJECT_BUNDLE_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_add_subject_layout, null);

        dataBaseIO = new DataBaseIO(context);
        subjects = dataBaseIO.getSubjects();

        imageView = (ImageView) view.findViewById(R.id.imageViewSubject);
        listView = (ListView) view.findViewById(R.id.addSubjectListView);
        editText = (EditText) view.findViewById(R.id.subjectEditText);
        submitButton = (ImageButton) view.findViewById(R.id.submitButtonSubject);

        listAdapter = new ListAdapterSubjects(context, subjects);
        listView.setAdapter(listAdapter);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }
                            @Override
                            public void onDismiss(ListView listView,
                                                  int[] reverseSortedPositions) {
                                addSubjectDialogListener.onFinishSubjectDeleteDialog(
                                        subjects, reverseSortedPositions[0]);
                                updateSubjectsList();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editText.getText().toString();

                if (subject.equals("")) {
                    Toast.makeText(context, "Subject field empty", Toast.LENGTH_SHORT).show();
                } else {
                    editText.setText("");
                    addSubjectDialogListener.onFinishSubjectAddDialog(subject);
                    updateSubjectsList();
                }
            }
        });

        builder.setView(view);

        return builder.create();

    }

    public void updateSubjectsList() {
        subjects = dataBaseIO.getSubjects();

        listAdapter = new ListAdapterSubjects(context, subjects);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }

}
