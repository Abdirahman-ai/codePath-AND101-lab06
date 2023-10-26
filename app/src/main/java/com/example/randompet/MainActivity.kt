package com.example.randompet
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var petList: MutableList<Pet>
    private lateinit var rvPets: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        petList = mutableListOf()
        rvPets = findViewById(R.id.pet_list)

        val button: Button = findViewById(R.id.petButton)
        button.setOnClickListener { refreshPetList() }

        refreshPetList()
    }

    private fun refreshPetList() {
        val client = AsyncHttpClient()
        val apiUrl = "https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=live_mfZkT8lsmYydy4F4DbBZO8egXQ8YwFzsFCx4WKCQWu0gBqg96jK5nJfgdnCJTE10"
        client[apiUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                petList.clear() // Clear the existing list
                if (json != null) {
                    val petArray = json.jsonArray
                    for (i in 0 until petArray.length()) {
                        val petObject = petArray.getJSONObject(i)
                        val imageUrl = petObject.getString("url")

                        val breedsArray = petObject.getJSONArray("breeds")
                        val breedObject = breedsArray.getJSONObject(0)
                        val name = breedObject.getString("name")
                        val description = breedObject.getString("description")

                        petList.add(Pet(imageUrl, name, description))
                    }
                    val adapter = PetAdapter(petList)
                    rvPets.adapter = adapter
                    rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.d("Dog Error", response ?: "Unknown error")
            }
        }]
    }
}

