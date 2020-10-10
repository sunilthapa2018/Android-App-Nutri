package com.example.nutri.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nutri.Database.Profile.Profile;
import com.example.nutri.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private Button btnSave;
    private TextView txtName;
    private TextView txtAge;
    private Spinner txtSex;
    private TextView txtHeight;
    private TextView txtWeight;

    private List<Profile> datas = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.getAllData().observe(getActivity(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if (profiles.size()>0) {
                    Profile currentData = profiles.get(0);
                    String name = currentData.getName();

                    txtName.setText(name);
                    txtAge.setText(String.valueOf(currentData.getAge()));
                    String sex = currentData.getSex().toString();
                    if(sex.toLowerCase().equals("male")){
                        txtSex.setSelection(0);
                    }else{
                        txtSex.setSelection(1);
                    }
                    //txtSex.setSelection(currentData.getSex());
                    txtHeight.setText(String.valueOf(currentData.getHeight()));
                    txtWeight.setText(String.valueOf(currentData.getWeight()));

                }
            }
        });

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSave = root.findViewById(R.id.btnSave);
        txtName = root.findViewById(R.id.txtName);
        txtAge = root.findViewById(R.id.txtAge);
        txtSex = (Spinner) root.findViewById(R.id.txtSex);
        txtHeight = root.findViewById(R.id.txtHeight);
        txtWeight = root.findViewById(R.id.txtWeight);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEverythingValid()){
                    String name;
                    String sex;
                    int age;
                    int height;
                    int weight;

                    name = txtName.getText().toString();
                    age = Integer.parseInt(txtAge.getText().toString());
                    sex = txtSex.getSelectedItem().toString();
                    height = Integer.parseInt(txtHeight.getText().toString());
                    weight = Integer.parseInt(txtWeight.getText().toString());

                    profileViewModel.deleteAllNotes();
                    Profile profile = new Profile(name, age, sex, height, weight);
                    profileViewModel.insert(profile);
                    showMsg("Data Saved");
                }
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.sex_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        txtSex.setAdapter(adapter);

        return root;
    }

    private boolean isEverythingValid(){
        if(txtName.getText().toString().isEmpty()){
            txtName.hasFocus();
            showMsg("Please Fill all boxes to save");
            return false;
        }else if(txtAge.getText().toString().isEmpty()){
            txtAge.hasFocus();
            showMsg("Please Fill all boxes to save");
            return false;
        }else if(txtHeight.getText().toString().isEmpty()){
            txtHeight.hasFocus();
            showMsg("Please Fill all boxes to save");
            return false;
        }else if(txtWeight.getText().toString().isEmpty()){
            txtWeight.hasFocus();
            showMsg("Please Fill all boxes to save");
            return false;
        }else if(Integer.parseInt(txtHeight.getText().toString())<0){
            txtHeight.hasFocus();
            showMsg("Please Enter Valid Number");
            return false;
        }else if(Integer.parseInt(txtWeight.getText().toString())<0){
            txtWeight.hasFocus();
            showMsg("Please Enter Valid Number");
            return false;
        }else{
            return true;
        }


    }

    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
    }
}