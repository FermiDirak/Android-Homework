package manuele.bryan.homework.dialogs;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import manuele.bryan.homework.helpers.ColorHelper;
import manuele.bryan.homework.helpers.DatesHelper;
import manuele.bryan.homework.R;

public class AddAssignmentDialog extends DialogFragment {
    public interface AddAssignmentDialogListener {
        void onFinishAssignmentEditDialog(String[] inputText);
        void openSubjectManagerFromAssignmentEditDialog();
    }

    public static final String SUBJECT_BUNDLE_KEY = "subjects";

    private Context context;

    public AddAssignmentDialogListener addAssignmentDialogListener;

    private ImageView imageView;
    private EditText titleEditText;
    private TextView dateDisplay;
    private EditText dateEditTextMonth;
    private EditText dateEditTextDay;
    private EditText dateEditTextYear;
    private Spinner subjectSpinner;
    private Button submitButton;

    List<String> subjects;

    int colorFrom = 0;



    public static AddAssignmentDialog newInstance(ArrayList<String> subjects) {
        AddAssignmentDialog addAssignmentDialog = new AddAssignmentDialog();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SUBJECT_BUNDLE_KEY, subjects);
        addAssignmentDialog.setArguments(bundle);

        return addAssignmentDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjects = getArguments().getStringArrayList(SUBJECT_BUNDLE_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_add_assignment_layout, null);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        titleEditText = (EditText) view.findViewById(R.id.titleEditText);
        dateDisplay = (TextView) view.findViewById(R.id.todaysDate);
        dateEditTextMonth = (EditText) view.findViewById(R.id.dueDateEditTextMonth);
        dateEditTextDay = (EditText) view.findViewById(R.id.dueDateEditTextDay);
        dateEditTextYear = (EditText) view.findViewById(R.id.dueDateEditTextYear);
        subjectSpinner = (Spinner) view.findViewById(R.id.subjectSpinner);
        submitButton = (Button) view.findViewById(R.id.submitButton);

        GregorianCalendar date = new GregorianCalendar();
        dateDisplay.setText("todays date: " + DatesHelper.dateToString(date));
        date.add(GregorianCalendar.DAY_OF_MONTH, 1);
        String[] dateTomorrow = DatesHelper.getDate(date);

        dateEditTextMonth.setText(dateTomorrow[0] + "");
        dateEditTextDay.setText(dateTomorrow[1] + "");
        dateEditTextYear.setText(dateTomorrow[2] + "");

        if (subjects.size() == 0) {
            Toast.makeText(context, "No classes yet", Toast.LENGTH_SHORT).show();

            addAssignmentDialogListener.openSubjectManagerFromAssignmentEditDialog();
            dismiss();
        }

        final String[] subjectArray = new String[subjects.size() + 1];
        for (int i = 0; i < subjects.size(); i++) {
            subjectArray[i] = subjects.get(i);
        }

        subjectArray[subjectArray.length-1] = "Add another subject...";
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, subjectArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(spinnerArrayAdapter);

        Typeface robotoFont = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Light.ttf");

        submitButton.setTypeface(robotoFont);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleText = titleEditText.getText().toString();
                String month = dateEditTextMonth.getText().toString();
                String day = dateEditTextDay.getText().toString();
                String year = dateEditTextYear.getText().toString();

                int monthInt = (Integer.parseInt(month));
                int dayInt = (Integer.parseInt(day));
                int yearInt = (Integer.parseInt(year));

                Calendar mycal = new GregorianCalendar();
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);


                if (yearInt < 100 && yearInt >= 0) {
                    yearInt += 2000;
                    year = yearInt + "";
                }
                String date = (monthInt - 1) + "-" + dayInt + "-" + yearInt;
                String subject = String.valueOf(subjectSpinner.getSelectedItem());

                if (subjectSpinner.getSelectedItemPosition() == subjectArray.length-1) {
                    Toast.makeText(context, "select a subject", Toast.LENGTH_SHORT).show();
                } else if (titleText.equals("") || date.length() <= 2 || subject.equals("")) {
                    Toast.makeText(context, "fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (DatesHelper.difference(DatesHelper.stringToDate(date)) == -1) {
                    Toast.makeText(context, "due date has already been passed!", Toast.LENGTH_SHORT).show();
                } else if (monthInt < 0 || monthInt > 12 || dayInt < 0 || dayInt > daysInMonth) {
                    Toast.makeText(context, "month or day doesn't exist", Toast.LENGTH_SHORT).show();
                } else {
                        String[] results = new String[3];
                        results[0] = titleText;
                        results[1] = date;
                        results[2] = subject;

                        addAssignmentDialogListener.onFinishAssignmentEditDialog(results);
                        dismiss();
                }
            }
        });


        String firstSubjectInList = String.valueOf(subjectSpinner.getItemAtPosition(0));
        colorFrom = ColorHelper.generateIntFromString(firstSubjectInList);

        //get the position of the object being selected
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == subjectArray.length-1) {
                    addAssignmentDialogListener.openSubjectManagerFromAssignmentEditDialog();
                    dismiss();

                    return;
                }

                String subject = String.valueOf(subjectSpinner.getItemAtPosition(position));
                int colorTo = ColorHelper.generateIntFromString(subject);

                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        imageView.setBackgroundColor((Integer) animator.getAnimatedValue());
                        submitButton.setBackgroundColor((Integer) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();

                colorFrom = colorTo;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;
    }
}
