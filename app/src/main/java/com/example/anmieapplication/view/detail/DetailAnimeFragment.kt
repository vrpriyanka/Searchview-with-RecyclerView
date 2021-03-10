package com.example.anmieapplication.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.anmieapplication.databinding.FragmentDetailAnimeBinding
import com.example.anmieapplication.utils.Logger
import java.lang.StringBuilder


class DetailAnimeFragment : Fragment() {

    private lateinit var binding: FragmentDetailAnimeBinding
    private val navigationArgs: DetailAnimeFragmentArgs by navArgs()

    companion object {
        var TAG = "DetailAnimeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animeItem = navigationArgs.argAnimeItem
        Logger.printLog(TAG, animeItem.toString())
        Glide.with(requireActivity())
            .load(animeItem.imageUrl)
            .centerCrop()
            .into(binding.imageAnime)

        binding.nameAnime.text = animeItem.title
        binding.synopsis.text = animeItem.synopsis
        binding.animeSiteurl.text = animeItem.url

        val sb = StringBuilder()
        sb.append("Episodes: ").append(animeItem.episodes)
        binding.episodeNum.text = sb

        val sb1 = StringBuilder()
        sb1.append(" Members: ").append(animeItem.members)
        binding.membersAnime.text = sb1

        val sb2 = StringBuilder()
        sb2.append("Type: ").append(animeItem.type)
        binding.animeType.text = sb2

        val sb3 = StringBuilder()
        sb3.append("Rated: ").append(animeItem.rated)
        binding.ratedNum.text = sb3
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.printLog(TAG, "in onDestroy")
    }
}