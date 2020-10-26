package school.comp7031.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;


public class SaveBMPTask extends AsyncTask<Object, Void, Void> {

    static Integer saveCount = 1;

    @Override
    protected Void doInBackground(Object... imageAndContextWrapper) {
        Bitmap image = (Bitmap)imageAndContextWrapper[0];
        ContextWrapper context = (ContextWrapper) imageAndContextWrapper[1];

        SQLiteDatabase db = context.openOrCreateDatabase("images.db", 0, null);

        if(!db.isOpen()) {
            Log.println(Log.WARN, "img_save", "Couldn't open DB");
            return null;
        }
        db.beginTransaction();
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + "images" + " (name VARCHAR, data BLOB);");

            ByteBuffer imageBuffer = ByteBuffer.allocate(image.getByteCount());
            image.copyPixelsToBuffer(imageBuffer);

            ContentValues cv = new ContentValues(2);
            cv.put("name", "tomcat_" + saveCount.toString());
            cv.put("data", (byte[])imageBuffer.array());

            db.insert("images",null, cv);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.println(Log.WARN, "img_save", "Exception while saving image: " + e.getMessage());
        }
        finally {
            db.endTransaction();
            saveCount++;
            if(db.isOpen()) {
                db.close();
            }
        }

        return null;
    }
}
