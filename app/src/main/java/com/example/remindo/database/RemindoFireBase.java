package com.example.remindo.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.remindo.Adapter.RemindoAdapter;
import com.example.remindo.ViewModels.RemindoViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
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
    FirebaseFirestore db;
    CollectionReference toDoCollectionReference;
    RemindoAdapter adapter;
    List<DocumentSnapshot> documentSnapshotList = new ArrayList<>();
    public RemindoFireBase(RemindoAdapter adapter) {
        if(db==null){
            db = FirebaseFirestore.getInstance();
        }
        this.adapter = adapter;
        toDoCollectionReference = db.collection("Users")
                .document("1")
                .collection("ToDo");

        toDoCollectionReference.orderBy("priority").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case MODIFIED:
                        case REMOVED:
                            documentSnapshotList = value.getDocuments();
                            adapter.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });


    }

    protected RemindoFireBase(Parcel in) {
        if(db==null){
            db = FirebaseFirestore.getInstance();
        }
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

    public void addToFireBase(RemindoViewModel rvm){
        toDoCollectionReference
                .add(rvm)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                });
    }

    public void readFromFireBase(){
        db.collection("Users")
                .document("1")
                .collection("ToDo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       documentSnapshotList = task.getResult().getDocuments();
                       adapter.notifyDataSetChanged();
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