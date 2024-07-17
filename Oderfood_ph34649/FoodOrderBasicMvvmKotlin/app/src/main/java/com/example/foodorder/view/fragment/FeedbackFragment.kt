package com.example.foodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentFeedbackBinding
import com.example.foodorder.model.Feedback
import com.example.foodorder.view.BaseFragment
import com.example.foodorder.view.activity.MainActivity

class FeedbackFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false)
        val feedback = Feedback()
        fragmentFeedbackBinding.feedbackModel = feedback
        return fragmentFeedbackBinding.root
    }

    override fun initToolbar() {
        val mainActivity = activity as MainActivity?
        mainActivity?.setToolBar(true, getString(R.string.feedback))
    }
}