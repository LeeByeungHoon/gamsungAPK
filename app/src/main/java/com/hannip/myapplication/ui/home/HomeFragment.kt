package com.hannip.myapplication.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hannip.myapplication.databinding.FragmentHomeBinding
import com.hannip.myapplication.ui.module.GetContent1


class HomeFragment : Fragment()  {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var i = -1
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val contentT: TextView = binding.content
        val likeB : Button = binding.likeB
        val dislikeB : Button = binding.dislikeB
        homeViewModel.text.observe(viewLifecycleOwner) {
            // 버튼을 누르면 다음 글을 보여줌
            contentT.text = getContentValue()
            likeB.setOnClickListener {
                contentT.text = getContentValue()
            }
            dislikeB.setOnClickListener {
                contentT.text = getContentValue()
            }
        }
        return root
    }
    private fun getContentValue (): String {
        i++
        return GetContent1().getContent1Data(i).Content+"\n" + GetContent1().getContent1Data(i).writer
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}