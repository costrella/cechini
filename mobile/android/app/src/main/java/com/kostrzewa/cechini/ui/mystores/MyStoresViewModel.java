package com.kostrzewa.cechini.ui.mystores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyStoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyStoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}