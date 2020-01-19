package com.development.task.triphava.ui.searchResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.development.task.triphava.imageLoader
import com.development.task.triphava.model.Status
import com.development.task.triphava.model.Trips
import com.development.task.triphava.ui.FilterModel
import com.development.task.triphava.ui.TRIPS_FILTERATION
import com.development.task.triphava.viewModel.TripResultViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*
import androidx.appcompat.app.AppCompatActivity
import com.development.task.triphava.MainActivity


val TRIPS = "trips"

class SearchResultFragment : Fragment(), SearchResultAdapter.OnItemClicked {

    private lateinit var tripsList: List<Trips>
    lateinit var tripResViewModel: TripResultViewModel
    var tripsFilterModel :FilterModel? = null


    override fun onClick(trips: Trips) {
        var bundle = Bundle()
        bundle.putParcelable(TRIPS, trips)

        view?.findNavController()
            ?.navigate(com.development.task.triphava.R.id.action_fragment_search_result_to_fragment_trip_details, bundle)

    }

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            com.development.task.triphava.R.layout.fragment_search_result,
            container,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripResViewModel = ViewModelProviders.of(this).get(TripResultViewModel::class.java)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            tripsFilterModel =
                arguments!!.getParcelable<FilterModel>(TRIPS_FILTERATION)
            }

        searchResultAdapter = SearchResultAdapter(this, imageLoader = imageLoader)
        recyclerView.adapter = searchResultAdapter
        lifecycleScope.launchWhenStarted {
                tripResViewModel.getTripsList(tripsFilterModel)

            tripResViewModel.tripsList.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> Toast.makeText(
                        activity,
                        "Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                    Status.SUCCESS -> {
                        Toast.makeText(
                            activity,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        it.data?.trips?.let { it1 ->
                            tripsList = it1
                            (activity as MainActivity)?.supportActionBar?.setTitle("Trips {${tripsList.size}}")
                            searchResultAdapter.setData(it1)
                        }

                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            activity,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })


        }

    }


}

