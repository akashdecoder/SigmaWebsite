package com.website.sigma.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.website.sigma.model.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService
{

    public static final String COL_NAME = "recruitments_2022";

    public String saveUser(User user) throws InterruptedException, ExecutionException
    {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection(COL_NAME)
                .document(user.getUsn()).set(user);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recruitments_2022");
        String key = reference.push().getKey();

        reference.child(key).setValue(user, null);

        return apiFuture.get().getUpdateTime().toString();
    }

    public User getUser(String usn) throws InterruptedException, ExecutionException
    {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COL_NAME).document(usn);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = apiFuture.get();

        User user = null;

        if(documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
            return user;
        } else {
            return null;
        }
    }

    public List<User> getAllUsers()
    {

        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionReference = firestore.collection(COL_NAME);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();

        try {
            List<User> userList = querySnapshotApiFuture.get().toObjects(User.class);
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.<User>emptyList();
    }


    public String deleteUser(String usn) throws InterruptedException, ExecutionException
    {
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> writeResultApiFuture = firestore.collection(COL_NAME).document(usn).delete();

        return "Deleted";
    }
}

