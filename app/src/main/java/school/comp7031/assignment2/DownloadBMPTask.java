package school.comp7031.assignment2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadBMPTask extends AsyncTask<ImageView, Void, Bitmap> {
    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(ImageView... iv) {
        imageView = iv[0];
        Bitmap result = null;
        BufferedReader br = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL( "http://10.0.2.2:8080/tomcat.bmp").openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            result = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            Log.println(Log.WARN,"image_GET", "Error getting image from server");
            Log.println(Log.WARN, "image_GET", e.getMessage());
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
