package com.grifalion.imageload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText edSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        edSearch = findViewById(R.id.edSearch);
        btnSearch = findViewById(R.id.btnSearch);
    }

    public void onClickSearch(View view) {
        new ImageDownloader().execute(edSearch.getText().toString());
    }
    private class ImageDownloader extends AsyncTask<String,Void,Bitmap>{
        HttpsURLConnection httpsURLConnection;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url  = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                return temp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), R.string.successful, Toast.LENGTH_SHORT).show();
            } else {
                imageView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),R.string.error,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
