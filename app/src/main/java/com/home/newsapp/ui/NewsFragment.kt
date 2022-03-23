package com.home.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.newsapp.R
import com.home.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "NewsFragment"

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter()
        binding.apply {
            recyclerViewNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.news.observe(viewLifecycleOwner) { listOfNews ->
                newsAdapter.submitList(listOfNews)
            }
        }

    }

}