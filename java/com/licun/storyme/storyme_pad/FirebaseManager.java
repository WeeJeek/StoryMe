package com.licun.storyme.storyme_pad;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//import static com.licun.storyme.storyme_pad.PublicVariables.mPhotos;


public class FirebaseManager {

    private DatabaseReference _mDatabase;
    private DatabaseReference _mQuestionRef;
    private DatabaseReference _mPhotoRef;
    private DatabaseReference _mRecordRef;

    private FirebaseAuth _mAuth;
    private FirebaseUser _mUser;
    private StorageReference _mStorageReference;
    private StorageReference _mPhotoStorageRef;
    private StorageReference _mAudioStorageRef;

    private ArrayList<Photo> _photos;

    public ArrayList<Photo> get_photos() {
        return _photos;
    }

    public void set_photos(ArrayList<Photo> _photos) {
        this._photos = _photos;
    }

    public ArrayList<Question> get_questions() {
        return _questions;
    }

    public void set_questions(ArrayList<Question> _questions) {
        this._questions = _questions;
    }

    private ArrayList<Question> _questions;
    final String path = Environment.getExternalStorageState();


    public FirebaseManager(){/*
        mPhotos = new ArrayList<>();
        PublicVariables.mQuestions = new ArrayList<>();
        this._mAuth = FirebaseAuth.getInstance();
        this._mUser = this._mAuth.getCurrentUser();
        this._mDatabase = FirebaseDatabase.getInstance().getReference();
        this._mQuestionRef = this._mDatabase.child("Question").child(_mUser.getUid());
        this._mPhotoRef = this._mDatabase.child("Photo").child(_mUser.getUid());
        this._mRecordRef = this._mDatabase.child("Audio").child(_mUser.getUid());

        this._mStorageReference = FirebaseStorage.getInstance().getReference();
        this._mPhotoStorageRef = FirebaseStorage.getInstance().getReference("Images");
        this._mAudioStorageRef = FirebaseStorage.getInstance().getReference("Audio");

        this._photos = new ArrayList<>();

        this.set_listeners();
    }

    public void set_listeners(){
        this.set_photo_listener();
        this.set_question_listener();
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
                        mPhotos = new ArrayList<Photo>();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                            mPhotos.add(dsp.getValue(Photo.class));
                        }
                        determine_download_Photo();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    //record
    public void
    _record(String file_path, final String uid, final Date create_time, final Context context, final String type){
        final Uri uri_1 = Uri.fromFile(new File(file_path));
        int index=file_path.lastIndexOf('/');
        final String file_name = file_path.substring(index, file_path.length()-1);
        final StorageReference ref = _mAudioStorageRef.child(file_name);
        UploadTask uploadTask = ref.putFile(uri_1);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    save_record_to_database_entry(context,uid,create_time,downloadUri.toString(),type, file_name);
                } else {
                }
            }
        });

//        taskSnapshotStorageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<? extends Object>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if(!task.isSuccessful()){
//                    throw task.getException();
//                }
//                return ref.getDownloadUrl();
//            }
//        });
    }

    public void save_record_to_database_entry(Context context, String uid, Date create_time, String record, String type, String filename){
        Record mRecord = new Record(uid, create_time, record,type, filename);

        this._mRecordRef.push().setValue(mRecord);
    }

    public void download_File(String path, Photo photo) {
        StorageReference islandRef = _mPhotoStorageRef.child("photos").child(photo.getUri());

        File localfile = new File(path, photo.getCreate_time().toString());

        islandRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                return;// Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void determine_download_Photo() {
        String current_path = path + "/" +  _mUser.getUid();
        File directory = new File(current_path);
        String[] names = directory.list();
        try {
            List<String> namelist = Arrays.asList(names);
            for (Photo dphoto: mPhotos){
                if (!namelist.contains(dphoto.getCreate_time().toString())){
                    download_File(current_path, dphoto);
                }
            }
        }catch (NullPointerException e){
            for (Photo dphoto: mPhotos){
                    download_File(current_path, dphoto);
            }
        }

        return;

        }

*/
}}

