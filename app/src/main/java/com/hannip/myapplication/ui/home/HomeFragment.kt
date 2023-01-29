package com.hannip.myapplication.ui.home

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hannip.myapplication.*
import com.hannip.myapplication.databinding.FragmentHomeBinding
import com.hannip.myapplication.ui.adapter.WriterListAdapter
import com.hannip.myapplication.ui.module.ContentData
import com.hannip.myapplication.ui.module.WriterData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val contentT: TextView = binding.content
        val likeB : Button = binding.likeB
        val dislikeB : Button = binding.dislikeB
        val writerTop3List :RecyclerView = binding.writerTop3List
        val lm = LinearLayoutManager(context)
        val contentInput : EditText = binding.contentInput
        val contentSend : ImageButton = binding.contentSend
        writerTop3List.layoutManager = lm
//        writerTop3List.setHasFixedSize(true)
        homeViewModel.text.observe(viewLifecycleOwner) {
            // 버튼을 누르면 다음 글을 보여줌

            getWriterData()
            contentList()


            contentSend.setOnClickListener{
                if(contentInput.text.toString() != ""){
                    contentInsert(contentInput.text.toString())
                }
            }
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val getWriterListApi = MyApp().retrofit.create(GetWriterList::class.java)

    // writer top3 데이터 가져오기
    private fun getWriterData() {
        val writerTop3List :RecyclerView = binding.writerTop3List
        val lm = LinearLayoutManager(context)
        writerTop3List.layoutManager = lm
        val arrayList = arrayListOf<WriterData>()
        getWriterListApi.postRequest().enqueue(object:
            Callback<ArrayList<WriterData>> {
            override fun onResponse(call: Call<ArrayList<WriterData>>, response: Response<ArrayList<WriterData>>) {
                for (list in response.body()!!){
                    arrayList.add(list)
                }
                writerTop3List.adapter = WriterListAdapter(requireContext(), arrayList)
            }

            override fun onFailure(call: Call<ArrayList<WriterData>>, t: Throwable) {
                Log.d("response : ", t.toString())
            }
        })
    }

    private val getContentInsertApi = MyApp().retrofit.create(ContentInsert::class.java)
    // writer top3 데이터 가져오기
    private fun contentInsert(content: String)  {
        val contentInput : EditText = binding.contentInput
        getContentInsertApi.postRequest("altm885","qudgns1081",content).enqueue(object:
            Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                contentInput.setText("")
                Toast.makeText(context, "글 작성에 성공 했습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "글 작성 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val getContentListApi = MyApp().retrofit.create(GetContentAllList::class.java)
    // writer top3 데이터 가져오기
    private fun contentList()  {
        val arrayList = arrayListOf<ContentData>()
        val mainContent : LinearLayout = binding.mainContent
        val content : TextView = binding.content
        val userNickname : TextView = binding.userNickname
        var count = 1
        getContentListApi.postRequest().enqueue(object:
            Callback<ArrayList<ContentData>> {
            override fun onResponse(call: Call<ArrayList<ContentData>>, response: Response<ArrayList<ContentData>>) {
                for (list in response.body()!!){
                    arrayList.add(list)
                }
                if(arrayList.size >0) {
                    content.text = arrayList[0].content
                    userNickname.text = arrayList[0].userNickname
                    mainContent.setOnClickListener {
                        // 더블클릭으로 넘길 것인지, 3초마다 변경 할 것인지
                        content.text = arrayList[count%arrayList.size].content
                        userNickname.text = arrayList[count%arrayList.size].userNickname
                        count++
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<ContentData>>, t: Throwable) {

            }
        })
    }

}