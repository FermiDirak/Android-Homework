package manuele.bryan.homework.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import manuele.bryan.homework.FloatingActionButton;
import manuele.bryan.homework.databases.DataBaseIO;
import manuele.bryan.homework.dialogs.AddAssignmentDialog;
import manuele.bryan.homework.dialogs.AddSubjectDialog;
import manuele.bryan.homework.adapters.ListAdapterMain;
import manuele.bryan.homework.adapters.ListModel;
import manuele.bryan.homework.R;
import manuele.bryan.homework.SwipeDismissListViewTouchListener;
import manuele.bryan.homework.helpers.DatesHelper;


public class MainActivity extends Activity implements
        AddAssignmentDialog.AddAssignmentDialogListener,
        AddSubjectDialog.AddSubjectDialogListener {

    SharedPreferences prefs = null;

    DataBaseIO dataBaseIO;

    ListView listView;
    ListAdapterMain listAdapter;
    List<ListModel> assignments;
    List<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("manuele.bryan.homework", MODE_PRIVATE);

        dataBaseIO = new DataBaseIO(getBaseContext());

        FloatingActionButton fab = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_action_new))
                .withButtonColor(getResources().getColor(R.color.accent))
                .withGravity(Gravity.BOTTOM | Gravity.END)
                .withMargins(0, 0, 16, 16)
                .create();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClicked();
            }
        });

        assignments = dataBaseIO.getAssignments();
        subjects = dataBaseIO.getSubjects();


        listView = (ListView) findViewById(android.R.id.list);
        listAdapter = new ListAdapterMain(this, assignments);
        listView.setAdapter(listAdapter);

        TextView emptyText = (TextView) findViewById(android.R.id.empty);
        Typeface robotoFont = Typeface.createFromAsset(getBaseContext().getAssets(),
                "fonts/Roboto-Light.ttf");
        emptyText.setTypeface(robotoFont);

        listView.setEmptyView(emptyText);

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
                                deleteAssignment(reverseSortedPositions[0]);
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {


            firstRun();

            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    private void updateAssignmentsList() {
        assignments = dataBaseIO.getAssignments();

        listAdapter = new ListAdapterMain(this, assignments);
        listView.setAdapter(listAdapter);
    }

    private void deleteAssignment(int position) {
        dataBaseIO.deleteAssignment(assignments, position);
        updateAssignmentsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_add_subject:
                createAddSubjectDialog();
                return true;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void firstRun() {

        dataBaseIO.addSubject("Computer Science");
        dataBaseIO.addSubject("History");
        dataBaseIO.addSubject("Math");
        dataBaseIO.addSubject("Science");
        dataBaseIO.addSubject("Art");
        dataBaseIO.addSubject("English");

        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);

        int year = tomorrow.get(GregorianCalendar.YEAR);
        int month = tomorrow.get(GregorianCalendar.MONTH);
        int day = tomorrow.get(GregorianCalendar.DAY_OF_MONTH);

        String date = month + "-" + day + "-" + year;

        dataBaseIO.addAssignment("Add assignments through the plus button", "homework app",
                date);
        dataBaseIO.addAssignment("Swipe to delete assignments", "homework app",
                date);
        dataBaseIO.addAssignment("And enjoy :)", "homework app",
                date);

        System.out.println("date: " + date);

        updateAssignmentsList();
        subjects = dataBaseIO.getSubjects();
    }

    public void fabClicked() {
        subjects = dataBaseIO.getSubjects();

        AddAssignmentDialog myDialog = AddAssignmentDialog.newInstance((ArrayList<String>) subjects);
        myDialog.addAssignmentDialogListener = MainActivity.this;

        myDialog.show(getFragmentManager(), "myDialog1");
    }

    //TODO: step1. create a notification that displays the last assignment on list
    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!");

        Intent resultIntent = new Intent(this, MainActivity.class);

        //ensure that back button functionality is maintained
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.this);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1337, builder.build()); //the number is useless

    }

    public void createAddSubjectDialog() {
        subjects = dataBaseIO.getSubjects();

        AddSubjectDialog myDialog = AddSubjectDialog.newInstance((ArrayList<String>) subjects);
        myDialog.addSubjectDialogListener = MainActivity.this;

        myDialog.show(getFragmentManager(), "myDialog2");
    }

    @Override
    public void onFinishAssignmentEditDialog(String[] inputText) {
        //This is where the new item in the ListView is created

        String title = inputText[0];
        String date = inputText[1];
        String subject = inputText[2];

        dataBaseIO.addAssignment(title, subject, date);
        updateAssignmentsList();
    }

    @Override
    public void openSubjectManagerFromAssignmentEditDialog() {
        createAddSubjectDialog();
    }

    @Override
    public void onFinishSubjectAddDialog(String subject) {
        dataBaseIO.addSubject(subject);
        subjects = dataBaseIO.getSubjects();
    }

    @Override
    public void onFinishSubjectDeleteDialog(List<String> subjects, int pos) {
        dataBaseIO.deleteSubject(subjects, pos);
        this.subjects = dataBaseIO.getSubjects();
    }
}
