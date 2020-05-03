package com.infinitumcode.mymovieapp.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.infinitumcode.mymovieapp.R
import kotlinx.android.synthetic.main.overlap_loading_view_layout.view.*

class OverlapLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.overlap_loading_view_layout, this)
    }

    fun loadingStateType(stateType: STATETYPE) {
        when (stateType) {
            STATETYPE.LOADING -> {
                loadingView.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                ivIcon.visibility = View.GONE
            }
            STATETYPE.ERROR -> {
                loadingView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                ivIcon.visibility = View.VISIBLE
            }
            STATETYPE.DONE -> {
                loadingView.visibility = View.GONE
            }
        }
    }

    enum class STATETYPE {
        ERROR, LOADING, DONE
    }

}