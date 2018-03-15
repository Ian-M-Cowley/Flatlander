package com.flatlander.flatlander.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe


/**
 * Created by iancowley on 9/20/17.
 */
internal class DataOnSubscribe(private val ref: DatabaseReference) : SingleOnSubscribe<DataSnapshot> {

    override fun subscribe(emitter: SingleEmitter<DataSnapshot>) {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(dataSnapshot)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (!emitter.isDisposed) {
                    emitter.onError(databaseError.toException())
                }
            }
        }

        ref.addListenerForSingleValueEvent(listener)
    }
}