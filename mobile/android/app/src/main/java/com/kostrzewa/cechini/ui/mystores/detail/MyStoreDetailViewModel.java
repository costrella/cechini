package com.kostrzewa.cechini.ui.mystores.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyStoreDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    // TODO: Implement the ViewModel


    public MyStoreDetailViewModel() {
        this.mText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}
