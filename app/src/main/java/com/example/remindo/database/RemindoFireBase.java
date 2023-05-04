package com.example.remindo.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.remindo.Adapter.RemindoAdapter;
import com.example.remindo.Models.RemindoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class RemindoFireBase implements Parcelable {
    String TAG = "FireBase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference toDoCollectionReference;
    RemindoAdapter adapter;
    List<DocumentSnapshot> documentSnapshotList = new ArrayList<>();

    public RemindoFireBase(RemindoAdapter adapter) {
        this.adapter = adapter;
        toDoCollectionReference = db.collection("Users")
                .document("1")
                .collection("ToDo");

        toDoCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case MODIFIED:
                        case REMOVED:
                            readFromFireBase();
                            break;
                    }
                }
            }
        });


    }

    protected RemindoFireBase(Parcel in) {
        TAG = in.readString();
    }

    public static final Creator<RemindoFireBase> CREATOR = new Creator<RemindoFireBase>() {
        @Override
        public RemindoFireBase createFromParcel(Parcel in) {
            return new RemindoFireBase(in);
        }

        @Override
        public RemindoFireBase[] newArray(int size) {
            return new RemindoFireBase[size];
        }
    };

    public RemindoFireBase() {

    }

    public void addToFireBase(RemindoModel rvm){
        db.collection("Users")
                .document("1")
                .collection("ToDo")
                 .add(rvm)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
    }

    public void readFromFireBase(){
        db.collection("Users")
                .document("1")
                .collection("ToDo")
                .orderBy("done").orderBy("priority")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       documentSnapshotList = task.getResult().getDocuments();
                       adapter.notifyDataSetChanged();
                    }
                });
    }


    public void updateFireBaseData(String id, RemindoModel rvm){
        db.collection("Users")
                .document("1")
                .collection("ToDo")
                .document(id)
                .set(rvm)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Done Update Sucess " + id + "  " + rvm.getDone());
                    }
                });
    }
    public DocumentSnapshot getDocumentData(int pos){
        return documentSnapshotList.get(pos);
    }

    public int getSize(){
        return documentSnapshotList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(TAG);
    }
}