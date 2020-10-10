package com.example.nutri.ui.journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nutri.Database.Journal.Journal;
import com.example.nutri.R;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddJournal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //private JournalRepository journalRepository;
    private JournalViewModel journalViewModel;
    private TextView txtTitle;
    private EditText txtFoodName;
    private EditText txtFoodQuantity;
    private Spinner txtType;
    private EditText txtDate;
    private Button btnSave;
    private float protein;
    private float fats;
    private float carb;
    private int calories;
    private int foodId;
    private int foodQuantity;
    private String date = "";
    private static DecimalFormat df2 = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        calories = 0;
        protein = 0;
        fats = 0;
        carb = 0;
        //journalRepository = new JournalRepository(this.getApplication());
        journalViewModel = new JournalViewModel(this.getApplication());
        Intent intent = getIntent();
        calories = Integer.parseInt(removeComa(intent.getStringExtra("calories")));
        String foodName = intent.getStringExtra("foodName");
        protein = Float.parseFloat(removeComa(intent.getStringExtra("protein")));
        fats = Float.parseFloat(removeComa(intent.getStringExtra("fats")));
        carb = Float.parseFloat(removeComa(intent.getStringExtra("carb")));



        txtTitle = findViewById(R.id.txtTitle);
        txtFoodName = findViewById(R.id.txtFoodName);
        txtFoodName.setEnabled(false);
        txtFoodQuantity = findViewById(R.id.txtFoodQuantity);
        txtType = findViewById(R.id.txtType);
        txtDate = findViewById(R.id.txtDate);
        txtDate.setFocusable(false);


        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtType.setAdapter(adapter);

        txtFoodName.setText(foodName);

        if(intent.hasExtra("foodId")){
            btnSave.setText("Save Changes");
            txtTitle.setText("Edit Food Log");
            foodId = intent.getIntExtra("foodId",0);
            String date = intent.getStringExtra("date");
            String foodType = intent.getStringExtra("foodType");
            foodQuantity = intent.getIntExtra("foodQuantity",0);
            txtFoodQuantity.setText(String.valueOf(foodQuantity));
            txtDate.setText(changeDate2(date));
            if(foodType.toLowerCase().equals("breakfast")){
                txtType.setSelection(0);
            }else if(foodType.toLowerCase().equals("lunch")){
                txtType.setSelection(1);
            }else if(foodType.toLowerCase().equals("snack")){
                txtType.setSelection(2);
            }else if(foodType.toLowerCase().equals("dinner")){
                txtType.setSelection(3);
            }else{
                txtType.setSelection(0);
            }
        }else{
            foodId = 0;
            txtFoodQuantity.setText("100");
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            txtDate.setText(formattedDate);





        }
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = "";
                try {
                    Date tempDate=new SimpleDateFormat("MMM d, yyyy").parse(txtDate.getText().toString());
                    DialogFragment datePicker = new DatePickerFragment(tempDate);
                    datePicker.show(getSupportFragmentManager(), "date picker");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEverythingValid()){
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);

                    String  quantity = txtFoodQuantity.getText().toString();
                    float weight = Float.parseFloat(quantity);

                    if(foodId==0){
                        foodQuantity=100;
                    }

                    float newCalories = (weight/foodQuantity) * calories;
                    newCalories = Math.round(newCalories);
                    int newIntCal = (int) newCalories;

                    float newProtein = (weight/foodQuantity) * protein;
                    newProtein= Float.parseFloat(df2.format(newProtein));

                    float newFats = (weight/foodQuantity) * fats;
                    newFats = Float.parseFloat(df2.format(newFats));

                    float newCarb = (weight/foodQuantity) * carb;
                    newCarb = Float.parseFloat(df2.format(newCarb));
                    String newDate = "";
                    try {
                        Date tempDate=new SimpleDateFormat("MMM d, yyyy").parse(txtDate.getText().toString());
                        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        String formattedDate2 = df2.format(tempDate);
                        newDate = formattedDate2;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Journal journal = new Journal(foodName,
                            (int) weight,
                            txtType.getSelectedItem().toString(),
                            newDate,
                            newProtein,
                            newFats,
                            newCarb,
                            newIntCal
                    );
                    //Edit details
                    if(foodId!=0){
                        journal.setId(foodId);
                        journalViewModel.update(journal);
                        showMsg("Data has been sucessfully Updated");
                    }else{
                        //Add details
                        journalViewModel.insert(journal);
                        showMsg("Data has been sucessfully Saved");
                    }
                    AddJournal.this.finish();

                }else{
                    txtFoodQuantity.hasFocus();
                    showMsg("Enter valid quantity of Food");

                }
            }
        });
    }

    public String changeDate2(String date){
        String newDate = "";
        try {
            Date tempDate=new SimpleDateFormat("MM/dd/yyyy").parse(date);
            SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = df.format(tempDate);
            newDate = formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public String precise(float x) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        return nf.format(x);

    }

    private String removeComa(String line){
        String newLine = line.replaceAll("[^\\d.]+", "");
        return newLine;
    }

    private boolean isEverythingValid(){
        if(txtFoodQuantity.getText().toString().isEmpty() || txtFoodQuantity.getText().toString().equals("0")){
            return false;
        }else{
            return true;
        }
    }


    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        Date currentDate = calendar.getTime();
        String formattedDate = sdf.format(currentDate);

        txtDate.setText(formattedDate);
    }
}