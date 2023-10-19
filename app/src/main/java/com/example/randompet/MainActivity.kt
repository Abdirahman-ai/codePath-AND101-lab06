package com.example.randompet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private var petImageURL = ""
    private lateinit var petList: MutableList<String>
    private lateinit var rvPets: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDogImageURL()
        Log.d("petImageURL", "pet image URL set")

        //  gets the button component
        val button: Button = findViewById(R.id.petButton)
        // update the ImageView to show the random image retrieved from the Dog API
        val imageView: ImageView = findViewById(R.id.petImage)
        getNextImage(button, imageView)

        petList = mutableListOf()
        rvPets = findViewById(R.id.pet_list)

    }

    // function to get an image URL from the dog API
    private fun getDogImageURL(){
        val client = AsyncHttpClient() // instantiate an AsyncHttpClient object

        // set our client to get the data from  dog API
        client["https://dog.ceo/api/breeds/image/random/20", object :JsonHttpResponseHandler() {
            // The onSuccess() function executes if we are able to successfully call the API. The onFailure() function executes if there is an error.
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                // get the URL for the image from the rest of the response
                if (json != null) {
                    // retrieve the value for the key message from the JSON we obtain from the API as a String
                    // and set our petImage variable to this value.
                    //petImageURL = json.jsonObject.getString("message")

                    val petImageArray = json.jsonObject.getJSONArray("message")

                    for(i in 0 until petImageArray.length()){
                        petList.add(petImageArray.getString(i))
                    }
                    val adapter = PetAdapter(petList)
                    rvPets.adapter = adapter
                    rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
                }
                Log.d("Dog", "response successful$json")
            }
            // The onFailure() function executes if there is an error in the API call
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                if (response != null) {
                    Log.d("Dog Error", response)
                }
            }
        }]
    }

    private fun getNextImage(button: Button, imageView: ImageView){
        // respond when the user taps the button.
        // We want the getDogImageURL() function to be called when the button is tapped so we get a new random image,
        // then use the Glide library to load this image into the ImageView.
        button.setOnClickListener{
            getDogImageURL()
            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)
        }
    }
}