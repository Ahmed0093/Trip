package com.development.task.triphava.ui.searchResult

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.development.task.triphava.DataParser
import com.development.task.triphava.R
import com.development.task.triphava.model.Trips
import com.development.task.triphava.repository.COMPLETED_STATUS
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_trip_details.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class TripDetailsFragment : Fragment(), OnMapReadyCallback {
    private var gMap: GoogleMap? = null
    private val parentJob = Job()
    private var currentPolyline: Polyline? = null
     var place : LatLng? = null
    var place2 : LatLng? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
    var tripsModel: Trips? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_details, container, false)
    }

    override fun onStop() {
        super.onStop()
        parentJob.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            tripsModel =
                arguments!!.getParcelable<Trips>(TRIPS)
            costTotalValue.text = tripsModel?.cost.toString()
            start_time.text = tripsModel?.request_date.toString()
            final_cost.text = tripsModel?.cost.toString() + tripsModel?.cost_unit
            rating_bar.rating = tripsModel?.driver_rating?.toFloat() ?: 0F
            pickupText.text = tripsModel?.pickup_location ?: ""
            dropOfText.text = tripsModel?.dropoff_location ?: ""
            distTextValue.text = tripsModel?.distance.toString() + tripsModel?.distance_unit
            durationTextValue.text = tripsModel?.duration.toString() + tripsModel?.duration_unit
            statusText.text = tripsModel?.status ?: ""
            if (tripsModel?.status.equals(COMPLETED_STATUS)) {
                statusImage.setImageResource(R.drawable.ic_check_40dp)
            } else {
                statusImage.setImageResource(R.drawable.ic_cancel_black_24dp)
            }
        }
        initMap()

        btnGetDirection.setOnClickListener{
            //TODO Add Route As it needs billing Account
            place?.let { it1 -> place2?.let { it2 -> drawRoute(it1, it2, gMap) } }
        }
    }

    private lateinit var mapFragment: SupportMapFragment

    private fun initMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        gMap = map
         place = tripsModel?.dropoff_lat?.let {
            tripsModel?.dropoff_lng?.let { it1 ->
                LatLng(
                    it,
                    it1
                )
            }
        }!!
         place2 = tripsModel?.pickup_lat?.let {
            tripsModel?.pickup_lng?.let { it1 ->
                LatLng(
                    it,
                    it1
                )
            }
        }!!

        map?.addMarker(MarkerOptions().position(place!!).title("PickUP"))
        map?.addMarker(MarkerOptions().position(place2!!).title("DropOff"))
        map?.moveCamera(CameraUpdateFactory.newLatLng(place))

    }

    private fun drawRoute(
        place: LatLng,
        place2: LatLng,
        map: GoogleMap?
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            val data = downloadUrl(getDirectionsUrl(place, place2))
            val parser = parseData(data)
            var polyData = exec(parser)

            currentPolyline?.remove();
            currentPolyline = map?.addPolyline(polyData);
            //1
        }
    }

    private fun exec(result: List<List<HashMap<String, String>>>): PolylineOptions? {
        var points: ArrayList<LatLng>
        var lineOptions: PolylineOptions? = null
        // Traversing through all the routes
        var i = 0
        while (i < result.size) {
            points = ArrayList()
            lineOptions = PolylineOptions()
            // Fetching i-th route
            var path: List<HashMap<String, String>> = result.get(i)

            var j = 0
            while (j < path.size) {
                var point = path.get(j)
                var lat = (point.get("lat"))?.toDouble()
                var lng = (point.get("lng"))?.toDouble()
                var position = LatLng(lat ?: 0.0, lng ?: 0.0)
                points.add(position)
                j++
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);

            lineOptions.width(20F)
            lineOptions.color(Color.BLUE);
            //   }
            Log.d("mylog", "onPostExecute lineoptions decoded");
            i++
        }
        return lineOptions
    }

    private suspend fun parseData(jsonData: String): List<List<HashMap<String, String>>> =
        withContext(Dispatchers.IO) {
            val jObject: JSONObject
            var routes: List<List<HashMap<String, String>>> =
                ArrayList<ArrayList<HashMap<String, String>>>()
            try {
                jObject = JSONObject(jsonData)
                val parser = DataParser()

                routes = parser.parse(jObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext routes
        }

    @Throws(IOException::class)
    private suspend fun downloadUrl(strUrl: String): String = withContext(Dispatchers.IO) {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)

            urlConnection = url.openConnection() as HttpURLConnection

            urlConnection!!.connect()

            iStream = urlConnection!!.getInputStream()

            val br = BufferedReader(InputStreamReader(iStream))

            val sb = StringBuffer()

            var line = ""
            line = br.readLine()
            while (line != null) {
                sb.append(line)
            }

            data = sb.toString()

            br.close()

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return@withContext data
    }


    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude

        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude

        // Sensor enabled
        val sensor = "sensor=false"
        val mode = "mode=driving"

        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor&$mode"

        // Output format
        val output = "json"

        // Building the url to the web service

//https://maps.googleapis.com/maps/api/directions/json/
        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
    }

}


