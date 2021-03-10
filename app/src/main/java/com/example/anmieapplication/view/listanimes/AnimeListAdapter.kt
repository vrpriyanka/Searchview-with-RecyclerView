package com.example.anmieapplication.view.listanimes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anmieapplication.constants.AppConstants
import com.example.anmieapplication.data.response.SearchResultItem
import com.example.anmieapplication.databinding.ItemListAnimeBinding
import com.example.anmieapplication.utils.Logger

class AnimesListAdapter(private var listSearchResults: List<SearchResultItem>) :
    RecyclerView.Adapter<AnimesListAdapter.AnimesViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimesViewHolder {
        val binding =
            ItemListAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimesViewHolder, position: Int) {
        val itemAnimes = listSearchResults[position]
        Logger.printLog(AppConstants.LOG_TAG, "in onBindViewHolder $itemAnimes.name")
        holder.binding.nameAnimes.text = itemAnimes.title
        Glide.with(holder.binding.imageAnime.context)
            .load(itemAnimes.imageUrl)
            .centerCrop()
            .into(holder.binding.imageAnime)

        // Sets on Click listener for recyclerview and navigate with details
        holder.binding.containerItemAnime.setOnClickListener { itemView ->
            val actionDetailView =
                AnimeListFragmentDirections.actionItemClickToDetailAnimeFragment(itemAnimes)
            itemView.findNavController().navigate(actionDetailView)
        }
    }

    override fun getItemCount(): Int {
        return listSearchResults.size
    }

    fun addAnimeItems(animesItems: List<SearchResultItem>) {
        Logger.printLog(AppConstants.LOG_TAG, "in addAnimeItems")
        this.listSearchResults = animesItems
    }

    inner class AnimesViewHolder(var binding: ItemListAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)
}