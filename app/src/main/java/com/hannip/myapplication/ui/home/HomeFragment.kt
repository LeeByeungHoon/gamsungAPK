package com.hannip.myapplication.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannip.myapplication.GetWriterList
import com.hannip.myapplication.MyApp
import com.hannip.myapplication.databinding.FragmentHomeBinding
import com.hannip.myapplication.ui.adapter.WriterListAdapter
import com.hannip.myapplication.ui.module.MainFunction
import com.hannip.myapplication.ui.module.WriterData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        val writerTop3List :RecyclerView = binding.writerTop3List
        val lm = LinearLayoutManager(context)
        writerTop3List.layoutManager = lm
//        writerTop3List.setHasFixedSize(true)

        homeViewModel.text.observe(viewLifecycleOwner) {
            // 버튼을 누르면 다음 글을 보여줌
            contentT.text = getContentValue()
            likeB.setOnClickListener {
                contentT.text = getContentValue()
            }
            dislikeB.setOnClickListener {
                contentT.text = getContentValue()
            }
            getWriterData()

        }
        return root
    }
    private fun getContentValue (): String {
        i++
        return MainFunction().getContent1Data(i).Content+"\n" + MainFunction().getContent1Data(i).writer
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val getWriterListApi = MyApp().retrofit.create(GetWriterList::class.java)

    // writer top3 데이터 가져오기
    private fun getWriterData() {
        val writerTop3List :RecyclerView = binding.writerTop3List
        val arrayList = arrayListOf<WriterData>()
        getWriterListApi.postRequest().enqueue(object:
            Callback<ArrayList<WriterData>> {
            override fun onResponse(call: Call<ArrayList<WriterData>>, response: Response<ArrayList<WriterData>>) {
                for (list in response.body()!!){
                    arrayList.add(list)
                    Log.i("tag1", arrayList[0].userId)
                }
                writerTop3List.adapter = WriterListAdapter(requireContext(), arrayList)
            }

            override fun onFailure(call: Call<ArrayList<WriterData>>, t: Throwable) {
                Log.d("response : ", t.toString())
            }
        })
    }
}