package com.example.anmieapplication.view.listanimes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anmieapplication.constants.AppConstants.LOG_TAG
import com.example.anmieapplication.data.network.ApiHelper
import com.example.anmieapplication.data.network.RetrofitBuilder
import com.example.anmieapplication.data.repository.AnimeRepository
import com.example.anmieapplication.data.response.SearchResultItem
import com.example.anmieapplication.databinding.AnimeListFragmentBinding
import com.example.anmieapplication.utils.Logger
import com.example.anmieapplication.utils.Status
import com.example.anmieapplication.viewmodel.AnimesViewModel
import java.util.*
import kotlin.collections.ArrayList

class AnimeListFragment : Fragment() {

    private lateinit var animeListBinding: AnimeListFragmentBinding
    private lateinit var adapterAnimes: AnimesListAdapter
    private lateinit var viewModel: AnimesViewModel
    private var animesList = ArrayList<SearchResultItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        animeListBinding = AnimeListFragmentBinding.inflate(inflater, container, false)
        return animeListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterAnimes = AnimesListAdapter(animesList)
        val layoutManager = LinearLayoutManager(activity)
        animeListBinding.recyclerviewAnimes.layoutManager = layoutManager
        animeListBinding.recyclerviewAnimes.adapter = adapterAnimes
        animeListBinding.animeSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filterList = arrayListOf<SearchResultItem>()
        // running a for loop to compare elements.
        for (item in animesList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title?.toLowerCase(Locale.ROOT)
                    ?.contains(text.toLowerCase(Locale.ROOT)) == true
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filterList.add(item)
            }
        }
        if (filterList.isNotEmpty()) {
            // at last we are passing that filtered
            // list to our adapter class.
            adapterAnimes.apply {
                addAnimeItems(filterList)
                notifyDataSetChanged()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        viewModel = ViewModelProvider(this).get(AnimesViewModel::class.java)
        viewModel.setRepository(AnimeRepository(ApiHelper(RetrofitBuilder.apiService)))

        viewModel.getListAnimes().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Logger.printLog(LOG_TAG, "data loaded")
                        animeListBinding.progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        Logger.printLog(LOG_TAG, "Status.ERROR ")
                        animeListBinding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        Logger.printLog(LOG_TAG, "Status.LOADING")
                        animeListBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(listSearchResults: List<SearchResultItem>) {
        animesList = listSearchResults as ArrayList<SearchResultItem>
        adapterAnimes.apply {
            addAnimeItems(listSearchResults)
            notifyDataSetChanged()
        }
    }
}