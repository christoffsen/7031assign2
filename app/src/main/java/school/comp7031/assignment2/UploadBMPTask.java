package school.comp7031.assignment2;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class UploadBMPTask extends AsyncTask<Bitmap, Void, Void> {

    static Integer uploadCount = 1;

    @Override
    protected Void doInBackground(Bitmap... images) {
        HttpURLConnection urlConnection = null;
        try {
            Buffer b = ByteBuffer.allocate(images[0].getByteCount());
            urlConnection = (HttpURLConnection) new URL("http://10.0.2.2:8080/upload/tomcat_" + uploadCount.toString() +".png").openConnection();
            urlConnection.setRequestMethod("PUT");

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            images[0].compress(Bitmap.CompressFormat.PNG, 5, urlConnection.getOutputStream());

            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            CharBuffer result = CharBuffer.allocate(2048);
            reader.read(result);

            Log.println(Log.WARN, "image_PUT", reader.toString());

        } catch (Exception e) {
            Log.println(Log.WARN,"image_PUT", "Error getting image from server");
            Log.println(Log.WARN, "image_PUT", e.getMessage());
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            uploadCount++;
        }

        return null;
    }
}
