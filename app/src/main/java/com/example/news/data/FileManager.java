package com.example.news.data;

import android.graphics.Bitmap;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.app.App;
import com.example.news.data.database.FileNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import io.reactivex.Completable;
import io.reactivex.Single;

public class FileManager {
    private String saveImage(Bitmap image, String imageName) {
        String savedImagePath = null;
        String imageFileName = "JPEG_" + imageName + ".jpg";
        File storageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImageCash");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedImagePath;
    }

    public Completable deleteImageByPath(String pathToImage) {
        return Completable.create(emitter -> {
            File file = new File(pathToImage);
            boolean deleted = file.delete();
            if (!deleted)
                emitter.onError(new FileNotFoundException());
            else
                emitter.onComplete();
        });
    }

    public Single<String> saveImageByUrl(String url, String imageName) {
        return Single.create(emitter ->
                Glide.with(App.getApp())
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        emitter.onSuccess(saveImage(resource, imageName));
                        return false;
                    }
                }).submit());
    }
}
