package com.home.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.newsapp.R
import com.home.newsapp.data.News
import com.home.newsapp.databinding.FragmentNewsBinding
import com.home.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "NewsFragment"

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnItemClickListener {

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter(this)
        binding.apply {
            recyclerViewNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.news.observe(viewLifecycleOwner) { result ->
                newsAdapter.submitList(result.data)

                fragmentNewsProgressBar.isVisible =
                    result is Resource.Loading && result.data.isNullOrEmpty()

                fragmentNewsTextViewError.isVisible =
                    result is Resource.Error && result.data.isNullOrEmpty()
                fragmentNewsTextViewError.text = result.error?.localizedMessage
            }
        }
    }

    override fun onItemClick(news: News) {
        val openUrl = news.url
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(openUrl)
        startActivity(intent)
//        viewModel.onNewsClicked(news)
    }
}