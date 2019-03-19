package com.licun.storyme.storyme_phone;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.telephony.mbms.DownloadRequest;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.http.Url;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.firebase.ui.auth.ui.phone.SubmitConfirmationCodeFragment.TAG;

public class FirebaseManager{/*

    private DatabaseReference _mDatabase;
    private DatabaseReference _mQuestionRef;
    private DatabaseReference _mPhotoRef;
    private DatabaseReference _mRecordRef;

    private FirebaseAuth _mAuth;
    private FirebaseUser _mUser;
    private StorageReference _mStorageReference;
    private StorageReference _mPhotoStorageRef;


    public FirebaseManager(){
        PublicVariables.mPhotos = new ArrayList<>();
        PublicVariables.mQuestions = new ArrayList<>();
        this._mAuth = FirebaseAuth.getInstance();
        this._mUser = this._mAuth.getCurrentUser();
        this._mDatabase = FirebaseDatabase.getInstance().getReference();
        this._mQuestionRef = this._mDatabase.child("Question").child(_mUser.getUid());
        this._mPhotoRef = this._mDatabase.child("Photo").child(this._mUser.getUid());
        this._mRecordRef = this._mDatabase.child("Audio").child(this._mUser.getUid());

        this._mStorageReference = FirebaseStorage.getInstance().getReference();
        this._mPhotoStorageRef = FirebaseStorage.getInstance().getReference("Images");
        this.set_listeners();
    }

    public void set_listeners(){
        this.set_photo_listener();
        this.set_question_listener();
        this.set_audio_listener();
    }

    //photo
    public void save_photo_entry(String uid,  Uri uri){
        Photo p = new Photo( uid, uri);

        this._mPhotoRef.push().setValue(p);
    }

    public void upload_photo_to_storage(final Uri uri, final Context context) {
        StorageReference filepath = _mPhotoStorageRef.child(_mUser.getUid()).child(Calendar.getInstance().getTime().toString());

        StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG);
                save_photo_entry(_mUser.getUid(),uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Upload fail", Toast.LENGTH_LONG);
            }
        });
    }

    public void download_photos(final Context context, String uid){
        StorageReference sr = _mStorageReference.child("images/" + uid + "/");
    }

    //question
    public void save_question(String uid, String content){
        Question q = new Question(uid, content);

        this._mQuestionRef.push().setValue(q);
    }

    private void set_question_listener() {
        this._mQuestionRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PublicVariables.mQuestions = new ArrayList<Question>();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                            PublicVariables.mQuestions.add(dsp.getValue(Question.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void set_photo_listener() {
        this._mPhotoRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PublicVariables.mPhotos = new ArrayList<Photo>();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                            PublicVariables.mPhotos.add(dsp.getValue(Photo.class));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void set_audio_listener() {
        this._mRecordRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PublicVariables.mRecords = new ArrayList<Record>();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                            PublicVariables.mRecords.add(dsp.getValue(Record.class));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public Record check_photo_has_record(Photo photo){
        String uid = photo.getUid();
        Date create_time = photo.getCreate_time();
        for (Record record:PublicVariables.mRecords)
            if(record.getUid().equals(uid) && record.getResource_create_time().equals(create_time))
                return record;

        return null;
    }

    public Record check_question_has_record(Question question){
        String uid = question.getUid();
        Date create_time = question.getCreate_time();
        for (Record record:PublicVariables.mRecords)
            if(record.getUid().equals(uid) && record.getResource_create_time().equals(create_time))
                return record;

        return null;
    }

    private ArrayList<Record> get_record_entries(){


        return null;
    }

    public void download_audio(final Context context, Record record) {
        final String file_name = record.getFilename();
        StorageReference ref = _mStorageReference.child(file_name);

        download_file(context, file_name, ".3gpp", DIRECTORY_DOWNLOADS, Uri.parse(record.getUri()));
    }

    public void download_file(Context context, String filename, String file_extension, String destination_directory, Uri uri){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destination_directory, filename + file_extension);

        downloadManager.enqueue(request);
    }*/
}
