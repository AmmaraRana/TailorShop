package com.example.adminpanel.activites.todo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {


    // Converts a Uri to a File and copies it to the 'cache' folder of the app
    public static File from(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) return null;

        File tempFile = createTemporaryFile(context, getFileName(context.getContentResolver(), uri));
        writeInputStreamToFile(inputStream, tempFile);
        return tempFile;
    }

    // Creates a temporary file in the app's cache directory
    private static File createTemporaryFile(Context context, String fileName) throws IOException {
        File tempDir = new File(context.getCacheDir(), "tempFiles");
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            throw new IOException("Failed to create directory for temporary files");
        }
        return new File(tempDir, fileName);
    }

    // Retrieves the file name for a Uri
    private static String getFileName(ContentResolver contentResolver, Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (nameIndex != -1) {
                            result = cursor.getString(nameIndex);
                        }
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment(); // Fall back to the last segment of the Uri path
        }
        return result;
    }


    // Writes an InputStream to a File
    private static void writeInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            inputStream.close();
        }
    }

}
