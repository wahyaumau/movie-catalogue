package com.example.moviecataloguefinal.helper;

import android.database.Cursor;

public interface LoadItemsCallback {
    void postExecute(Cursor cursor);
}
