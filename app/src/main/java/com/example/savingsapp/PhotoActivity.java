package com.example.savingsapp;  // Define the package for the application

import android.annotation.SuppressLint;  // Import the SuppressLint annotation
import android.content.Intent;  // Import the Intent class for handling intents
import android.content.pm.PackageManager;  // Import the PackageManager class for handling permissions
import android.graphics.Bitmap;  // Import the Bitmap class for handling bitmaps
import android.net.Uri;  // Import the Uri class for handling URIs
import android.os.Bundle;  // Import the Bundle class for passing data between activities
import android.os.Environment;  // Import the Environment class for accessing environmental variables
import android.provider.MediaStore;  // Import the MediaStore class for media handling
import android.util.Log;  // Import the Log class for logging debug messages
import android.view.View;  // Import the View class for UI components
import android.widget.Button;  // Import the Button class for buttons

import androidx.appcompat.app.AppCompatActivity;  // Import the AppCompatActivity class for activity features
import androidx.core.content.ContextCompat;  // Import the ContextCompat class for context compatibility
import androidx.core.content.FileProvider;  // Import the FileProvider class for secure file sharing

import com.squareup.picasso.Picasso;  // Import the Picasso library for image loading

import java.io.File;  // Import the File class for file handling
import java.io.FileOutputStream;  // Import the FileOutputStream class for file output
import java.io.IOException;  // Import the IOException class for handling IO exceptions
import java.text.SimpleDateFormat;  // Import the SimpleDateFormat class for formatting dates
import java.util.Date;  // Import the Date class for handling dates

import de.hdodenhof.circleimageview.CircleImageView;  // Import the CircleImageView class for circular image views

public class PhotoActivity extends BaseActivity implements View.OnClickListener {  // Define the PhotoActivity class which extends BaseActivity and implements OnClickListener
    String currentPhotoPath;  // Declare a String variable to hold the current photo path
    String dbPhotoPath;  // Declare a String variable to hold the database photo path
    int REQUEST_IMAGE_CAPTURE = 1;  // Declare an integer constant for the image capture request code

    CircleImageView mImageView;  // Declare a CircleImageView variable for displaying the image
    Button captureButton;  // Declare a Button variable for the capture button
    Button saveButton;  // Declare a Button variable for the save button

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // Override the onCreate method to set up the activity
        super.onCreate(savedInstanceState);  // Call the superclass's onCreate method
        setContentView(R.layout.activity_photo);  // Set the content view to the layout defined in activity_photo.xml
        initViews();  // Initialize the views by calling the initViews method
        initAppDb();  // Initialize the application database
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {  // Define the createImageFile method to create an image file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());  // Create a timestamp for the file name
        String imageFileName = "JPEG_" + timeStamp + "_";  // Create the image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);  // Get the external files directory for pictures
        File image = File.createTempFile(
                imageFileName,  // Prefix for the file name
                ".jpg",  // Suffix for the file name
                storageDir  // Directory for the file
        );

        currentPhotoPath = image.getAbsolutePath();  // Save the file path for use with intents
        return image;  // Return the created file
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {  // Define the dispatchTakePictureIntent method to handle taking pictures
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  // Create an intent for capturing images
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {  // Ensure there's a camera activity to handle the intent
            File photoFile = null;  // Declare a File variable for the photo file
            try {
                photoFile = createImageFile();  // Create the image file
            } catch (IOException ex) {
                // Handle the error if file creation failed
            }
            if (photoFile != null) {  // Continue only if the file was successfully created
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);  // Get the URI for the file
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);  // Start the activity to capture the image
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  // Override the onActivityResult method to handle activity results
        super.onActivityResult(requestCode, resultCode, data);  // Call the superclass's onActivityResult method

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {  // Check if the request was for image capture and was successful
            Bundle extras = data.getExtras();  // Get the extras from the intent
            Bitmap imageBitmap = (Bitmap) extras.get("data");  // Get the image bitmap from the extras
            imageBitmap = cropAndScale(imageBitmap, 300);  // Crop and scale the image bitmap
            mImageView.setImageBitmap(imageBitmap);  // Set the image bitmap to the ImageView
            savePhoto(imageBitmap);  // Save the photo
        }
    }

    void savePhoto(Bitmap bitmap) {  // Define the savePhoto method to save the photo
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ginafi_photos");  // Create a directory for the photos
        if (!dir.exists()) {  // Check if the directory does not exist
            dir.mkdir();  // Create the directory
        }

        dbPhotoPath = dir.getAbsolutePath() + "/profile_photo.png";  // Set the database photo path
        Log.d("photoPath", dbPhotoPath);  // Log the photo path
        File file = new File(dir, "profile_photo.png");  // Create a file for the photo
        try (FileOutputStream out = new FileOutputStream(file)) {  // Create a FileOutputStream for the file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);  // Compress the bitmap and write to the file
            out.flush();  // Flush the output stream
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());  // Insert the image into the media store
        } catch (IOException e) {  // Catch any IO exceptions
            e.printStackTrace();  // Print the stack trace for the exception
        }
    }

    private void initViews() {  // Define the initViews method to initialize the views
        mImageView = findViewById(R.id.photo);  // Find the CircleImageView by its ID

        captureButton = findViewById(R.id.capture_btn);  // Find the capture Button by its ID
        captureButton.setOnClickListener(this);  // Set the OnClickListener for the capture button

        saveButton = findViewById(R.id.save_btn);  // Find the save Button by its ID
        saveButton.setOnClickListener(this);  // Set the OnClickListener for the save button
    }

    @Override
    public void onClick(View v) {  // Override the onClick method to handle click events
        if (v.getId() == R.id.capture_btn) {  // Check if the capture button is clicked
            if (!isPermissionGranted("android.permission.CAMERA")) {  // Check if the camera permission is not granted
                requestPermissions(new String[]{
                        "android.permission.CAMERA",  // Request camera permission
                        "android.permission.WRITE_EXTERNAL_STORAGE",  // Request write external storage permission
                        "android.permission.READ_EXTERNAL_STORAGE"  // Request read external storage permission
                }, 1);  // Request the permissions with request code 1
            } else {
                dispatchTakePictureIntent();  // Dispatch the take picture intent
            }
        } else if (v.getId() == R.id.save_btn) {  // Check if the save button is clicked
            if (dbPhotoPath == null) {  // Check if the database photo path is null
                showToast("Please take a photo first");  // Show a toast message indicating to take a photo first
                return;  // Return from the method
            }
            Intent intent = getIntent();  // Get the intent that started the activity
            long userId = intent.getLongExtra("userId", 0);  // Get the user ID from the intent
            runInBackground(() -> {  // Run the following code in a background thread
                appDatabase.userDao().updateUser((int) userId, dbPhotoPath);  // Update the user in the database with the photo path
                runOnUiThread(() -> {  // Run the following code on the UI thread
                    showToast("Photo saved successfully");  // Show a toast message indicating the photo was saved successfully
                    gotoActivity(this, MainActivity.class);  // Go to the MainActivity
                });
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {  // Override the onRequestPermissionsResult method to handle permission results
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);  // Call the superclass's onRequestPermissionsResult method
        if (requestCode == 1) {  // Check if the request code is 1
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  // Check if the permission was granted
                dispatchTakePictureIntent();  // Dispatch the take picture intent
            }
        }
    }

    public static Bitmap cropAndScale(Bitmap source, int scale) {  // Define the cropAndScale method to crop and scale a bitmap
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();  // Determine the smaller dimension of the source bitmap
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();  // Determine the larger dimension of the source bitmap
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;  // Calculate the x-coordinate for cropping
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;  // Calculate the y-coordinate for cropping
        source = Bitmap.createBitmap(source, x, y, factor, factor);  // Crop the bitmap to a square
        source = Bitmap.createScaledBitmap(source, scale, scale, false);  // Scale the bitmap to the specified size
        return source;  // Return the cropped and scaled bitmap
    }
}
