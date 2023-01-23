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
import com.hannip.myapplication.ui.module.MainFunction
import com.hannip.myapplication.ui.module.WriterData


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
        val writer1: TextView = binding.gsWriterNickName1
        val intro1: TextView = binding.gsWriterIntro1
        val follower1 : TextView = binding.gsWriterFollower1
        val writer2: TextView = binding.gsWriterNickName2
        val intro2: TextView = binding.gsWriterIntro2
        val follower2 : TextView = binding.gsWriterFollower2
        val writer3: TextView = binding.gsWriterNickName3
        val intro3: TextView = binding.gsWriterIntro3
        val follower3 : TextView = binding.gsWriterFollower3
        homeViewModel.text.observe(viewLifecycleOwner) {
            // 버튼을 누르면 다음 글을 보여줌
            contentT.text = getContentValue()
            likeB.setOnClickListener {
                contentT.text = getContentValue()
            }
            dislikeB.setOnClickListener {
                contentT.text = getContentValue()
            }
            val list = getWriterData()
            writer1.text = list[0].writer
            writer2.text = list[1].writer
            writer3.text = list[2].writer
            intro1.text = list[0].intro
            intro2.text = list[1].intro
            intro3.text = list[2].intro
            follower1.text = list[0].Like.toString()
            follower2.text = list[1].Like.toString()
            follower3.text = list[2].Like.toString()
        }
        return root
    }
    private fun getContentValue (): String {
        i++
        return MainFunction().getContent1Data(i).Content+"\n" + MainFunction().getContent1Data(i).writer
    }
    private fun getWriterData () : List<WriterData>{
        return MainFunction().getWriterData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}