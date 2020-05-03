package com.infinitumcode.mymovieapp.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.infinitumcode.mymovieapp.R
import kotlinx.android.synthetic.main.empty_view_layout.view.*

class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.empty_view_layout, this)
    }

    fun emptyStateType(stateType: STATETYPE, listener: OnClickListener?) {
        when (stateType) {
            STATETYPE.NOERROR -> {
                empty_state_root.visibility = View.GONE
            }
            STATETYPE.CONNECTION -> {
                empty_state_root.visibility = View.VISIBLE
                btn_retry.visibility = View.VISIBLE
                btn_retry.setOnClickListener(listener)
                iv_state.setImageResource(R.drawable.fatal_error)
                tv_desc.text = resources.getString(R.string.connection_error)
            }
            STATETYPE.OPERATIONAL -> {
                empty_state_root.visibility = View.VISIBLE
                btn_retry.visibility = View.GONE
                iv_state.setImageResource(R.drawable.fatal_error)
                tv_desc.text = resources.getString(R.string.operational_error_message)
            }
            STATETYPE.EMPTY -> {
                empty_state_root.visibility = View.VISIBLE
                btn_retry.visibility = View.GONE
                iv_state.setImageResource(R.drawable.empty_list)
                tv_desc.text = resources.getString(R.string.empty_error)
            }
        }
    }

    enum class STATETYPE {
        NOERROR, CONNECTION, OPERATIONAL, EMPTY
    }

}