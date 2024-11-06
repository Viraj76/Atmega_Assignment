package com.example.tasktwo

import androidx.paging.PagingSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class FirestorePagingSource(private val firestore: FirebaseFirestore) :
    PagingSource<QuerySnapshot, MyItem>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, MyItem> {
        try {
            val currentPage = params.key ?: firestore.collection("items")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .limit(params.loadSize.toLong())
                .get()
                .await()

            val data = currentPage.toObjects<MyItem>()

            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = currentPage.documents.lastOrNull()
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, MyItem>): QuerySnapshot? {
        return null
    }
}
