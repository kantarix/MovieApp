package com.flethy.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieItem = view.findViewById<ImageView>(R.id.movie_frame)
        movieItem.setOnClickListener { goToDetails() }
    }

    private fun goToDetails() {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            addToBackStack(null)
            add(R.id.persistent_container, FragmentMoviesDetails.newInstance(), "FragmentMoviesDetails")
            commit()
        }
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }

}