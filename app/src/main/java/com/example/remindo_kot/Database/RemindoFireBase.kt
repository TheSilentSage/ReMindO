package com.example.remindo_kot.Database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.example.remindo_kot.Adapter.RemindoAdapter
import com.example.remindo_kot.Models.RemindoModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RemindoFireBase() : Parcelable {
    val db = Firebase.firestore
    lateinit var toDoCollectionReference :CollectionReference
    lateinit var adapter:RemindoAdapter
    var documentSnapshotList: List<DocumentSnapshot> = ArrayList()

    constructor(parcel: Parcel) : this() {
    }

    constructor(adapter: RemindoAdapter) : this() {
        this.adapter = adapter
        toDoCollectionReference = db.collection("Users")
            .document("1")
            .collection("ToDo")
        toDoCollectionReference.addSnapshotListener { value, error ->
            for (dc in value!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.MODIFIED, DocumentChange.Type.REMOVED -> readFromFireBase()
                    else -> {break}

                }
            }
        }
    }

fun addToFireBase(rvm: RemindoModel,context: Context){
    db.collection("Users")
        .document("1")
        .collection("ToDo")
        .add(rvm)
        .addOnSuccessListener { documentReference ->
            Toast.makeText(context,"Task Added",Toast.LENGTH_SHORT).show()
            Log.d(CREATOR.TAG, "DocumentSnapshot added with ID: " + documentReference.id)
        }.addOnFailureListener(OnFailureListener { e -> println(e.message) })
}
    fun readFromFireBase(){
        db.collection("Users")
            .document("1")
            .collection("ToDo")
            .orderBy("done").orderBy("priority")
            .get()
            .addOnCompleteListener{task ->
                documentSnapshotList = task.getResult().documents
                adapter.notifyDataSetChanged()
            }
    }

    fun updateFireBaseData(id:String,rvm:RemindoModel){
        db.collection("Users")
            .document("1")
            .collection("ToDo")
            .document(id)
            .set(rvm)
            .addOnSuccessListener{unused ->
                println("Done Update Sucess " + id + "  " + rvm.done)
            }
    }

    fun getDocumentData(pos:Int): DocumentSnapshot {
        return documentSnapshotList.get(pos)
    }

    fun getSize(): Int {
        return documentSnapshotList.size
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RemindoFireBase> {
        override fun createFromParcel(parcel: Parcel): RemindoFireBase {
            return RemindoFireBase(parcel)
        }

        override fun newArray(size: Int): Array<RemindoFireBase?> {
            return arrayOfNulls(size)
        }

        private const val TAG = "RemindoFireBase"
    }


}