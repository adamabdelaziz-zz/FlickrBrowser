package com.androidadam.flickrbrowser

import android.net.sip.SipSession
import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import kotlin.Exception

class GetFlickrJsonData(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<Photo>>() {
    private val TAG = "GetFlickrJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground starts")

        val photoList = ArrayList<Photo>()

        try {
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")

            for(i in 0 until  itemsArray.length()){
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorID = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")
                //Nested JSON object
                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoURL = jsonMedia.getString("m")
                val link =  photoURL.replaceFirst("_m.jpg", "_b.jpg")

                val photoObject = Photo(title,author,authorID,link,tags,photoURL)

                photoList.add(photoObject)
                Log.d(TAG, ".doInBackground $photoObject")
            }
        }catch(e:JSONException){
            e.printStackTrace()
            Log.e(TAG, ".doInBackground error processing Json Data ${e.message}")
            cancel(true)
            listener.onError(e)
        }
        Log.d(TAG, ".doInBackground ends")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, ".onPostExecute ends")
    }
}