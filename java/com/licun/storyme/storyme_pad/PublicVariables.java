package com.licun.storyme.storyme_pad;

import java.util.ArrayList;

class PublicVariables {

    private static FirebaseManager mFirebaseManager;

    private static ArrayList<Question> mQuestions;

    private static ArrayList<Photo> mPhotos;




    private static ArrayList<Question> getmQuestions() {
        return mQuestions;
    }

    private static void setmQuestions(ArrayList<Question> mQuestions) {
        PublicVariables.mQuestions = mQuestions;
    }

    private static ArrayList<Photo> getmPhotos() {
        return mPhotos;
    }

    private static void setmPhotos(ArrayList<Photo> mPhotos) {
        PublicVariables.mPhotos = mPhotos;
    }


}
