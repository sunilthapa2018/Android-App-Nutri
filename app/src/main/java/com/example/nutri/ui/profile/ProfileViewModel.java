package com.example.nutri.ui.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.nutri.Database.Profile;
import com.example.nutri.Database.ProfileRepository;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository repository;

    private LiveData<List<Profile>> allData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
        allData = repository.getAllProfiles();
    }

    public void insert(Profile profile){
        repository.insert(profile);
    }

    public void update(Profile profile){
        repository.update(profile);
    }

    public void delete(Profile profile){
        repository.delete(profile);
    }

    public void deleteAllNotes(){
        repository.deleteAllProfiles();
    }

    public LiveData<List<Profile>> getAllData() {
        return allData;
    }
}