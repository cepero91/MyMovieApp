package com.infinitumcode.mymovieapp.view.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.infinitumcode.mymovieapp.R


class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_navigation_home)
        }, 2000)
    }

}
