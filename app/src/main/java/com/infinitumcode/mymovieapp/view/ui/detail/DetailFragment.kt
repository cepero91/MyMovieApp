package com.infinitumcode.mymovieapp.view.ui.detail

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.data.Resource
import com.infinitumcode.mymovieapp.domain.pojo.MovieDetail
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.view.customview.OverlapLoadingView
import com.infinitumcode.mymovieapp.view.ui.detail.adapter.ActorListAdapter
import com.infinitumcode.mymovieapp.util.Constants
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject


class DetailFragment : Fragment(R.layout.fragment_detail), View.OnClickListener {

    private var snackbar: Snackbar? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailViewModel
    private lateinit var actorsAdapter: ActorListAdapter

    private var movieResult: MovieResult? = null

    private var movieIsFavorite: Boolean = false

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        movieResult = requireArguments().getParcelable("movie")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateCachedUI()
        obsState()
        obsData()
        viewModel.loadMovieDetail(movieResult!!.id)
    }


    private fun obsData() {
        viewModel.getMovieDetail().observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
        viewModel.movieIsFavorite(movieResult!!.id.toString())
            .observe(viewLifecycleOwner, Observer {
                changeFavoriteIcon(it.isNotEmpty())
            })
    }

    private fun changeFavoriteIcon(isFavorite: Boolean) {
        movieIsFavorite = isFavorite
        ivFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp)
    }

    private fun updateUI(resource: Resource<MovieDetail>?) {
        if (resource?.data != null) {
            if (resource.data.genres != null) {
                val stringCommaGnre = resource.data.genres.joinToString { it.name }
                tvGnreValue.text = stringCommaGnre
            }
            if (resource.data.runtime != null) {
                val hours = resource.data.runtime!! / 60
                val min = resource.data.runtime % 60
                tvDuration.text = String.format("%sh %smin", hours, min)
                tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_schedule_black_24dp,
                    0, 0, 0
                )
                tvDuration.compoundDrawablePadding = 5
            }
            if (!resource.data.overview.isNullOrEmpty()) {
                tvDescriptionValue.visibility = View.VISIBLE
                tvDescriptionTitle.visibility = View.VISIBLE
                tvDescriptionValue.text = resource.data.overview
            } else {
                tvDescriptionValue.visibility = View.GONE
                tvDescriptionTitle.visibility = View.GONE
            }
            if (resource.data.tagline.isNullOrEmpty()) {
                tvTaglineTitle.visibility = View.GONE
                tvQuoteValue.visibility = View.GONE
            } else {
                tvTaglineTitle.visibility = View.VISIBLE
                tvQuoteValue.visibility = View.VISIBLE
                tvQuoteValue.text = String.format("\"%s\"", resource.data.tagline)
            }
            actorsAdapter.submitList(resource.data.credits?.cast ?: listOf())
            loadingView.loadingStateType(OverlapLoadingView.STATETYPE.DONE)
            contentDescription.visibility = View.VISIBLE
        } else {
            loadingView.loadingStateType(OverlapLoadingView.STATETYPE.ERROR)
            snackbar =
                Snackbar.make(nestedDetail, "Ha perdido la conexión", Snackbar.LENGTH_INDEFINITE)
            snackbar!!.setAction("Reintentar") {
                snackbar!!.dismiss()
                Handler().postDelayed({
                    viewModel.loadMovieDetail(movieResult!!.id)
                }, 250)
            }
            snackbar!!.show()
        }
    }

    private fun obsState() {
        viewModel.getProgresState().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                loadingView.loadingStateType(OverlapLoadingView.STATETYPE.LOADING)
            }
        })
    }

    private fun updateCachedUI() {
        ivFavorite.setOnClickListener(this)
        Picasso.get().load(Constants.BASE_IMAGE_URL_w500_API + movieResult!!.backdrop_path)
            .error(R.drawable.logo)
            .into(ivBackdrop)
        Picasso.get().load(Constants.BASE_IMAGE_URL_API + movieResult!!.poster_path)
            .error(R.drawable.small_placeholder)
            .into(ivPoster)
        tvMovieTitleValue.text = movieResult!!.title
        if (movieResult!!.title.length > 10) {
            tvMovieTitleValue.isSelected = true
        }
        tvVoteAverage.text = movieResult!!.vote_average.toString()
        actorsAdapter = ActorListAdapter()
        actors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        actors.adapter = actorsAdapter
    }

    override fun onDestroyView() {
        if (snackbar != null && snackbar!!.isShown)
            snackbar!!.dismiss()
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivFavorite -> {
                viewModel.saveFavorite(movieResult!!, !movieIsFavorite)
            }
        }
    }

}
