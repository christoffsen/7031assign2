package school.comp7031.assignment2;

import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uploadClick(View view) {
        ImageView iview = (ImageView)findViewById(R.id.imageView);
        BitmapDrawable bmpDrw = (BitmapDrawable)iview.getDrawable();
        UploadBMPTask task = new UploadBMPTask();
        task.execute(bmpDrw.getBitmap());
    }

    public void saveClick(View view) {
        ImageView iview = (ImageView)findViewById(R.id.imageView);
        BitmapDrawable bmpDrw = (BitmapDrawable)iview.getDrawable();
        SaveBMPTask task = new SaveBMPTask();
        task.execute(bmpDrw.getBitmap(), new ContextWrapper(this.getApplicationContext()));
    }

    public void retrieveClick(View view) {
       DownloadBMPTask task = new DownloadBMPTask();
       task.execute((ImageView)findViewById((R.id.imageView)));
    }

}