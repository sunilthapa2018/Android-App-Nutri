package com.example.nutri;
public class FoodMenuItems {
    private int mImageResource;
    private String txtTitleText;

    public FoodMenuItems(int mImageResource, String txtTitleText) {
        this.mImageResource = mImageResource;
        this.txtTitleText = txtTitleText;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getTxtTitleText() {
        return txtTitleText;
    }

    public void setTxtTitleText(String txtTitleText) {
        this.txtTitleText = txtTitleText;
    }
}
