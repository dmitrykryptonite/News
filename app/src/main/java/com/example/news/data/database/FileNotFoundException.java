package com.example.news.data.database;

import androidx.annotation.Nullable;

import java.io.IOException;

public class FileNotFoundException extends IOException {
    @Nullable
    @Override
    public String getMessage() {
        return "File Not Found";
    }
}
