package com.hannip.myapplication.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannip.myapplication.GetWriterAllList
import com.hannip.myapplication.GetWriterList
import com.hannip.myapplication.MyApp
import com.hannip.myapplication.databinding.FragmentNotificationsBinding
import com.hannip.myapplication.ui.adapter.WriterListAdapter
import com.hannip.myapplication.ui.module.WriterData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {
            getWriterData()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val getWriterListApi = MyApp().retrofit.create(GetWriterAllList::class.java)

    // writer top3 데이터 가져오기
    private fun getWriterData() {
        val writerTop100List :RecyclerView = binding.writerTop100list
        val lm = LinearLayoutManager(context)
        writerTop100List.layoutManager = lm
        val arrayList = arrayListOf<WriterData>()
        getWriterListApi.postRequest().enqueue(object:
            Callback<ArrayList<WriterData>> {
            override fun onResponse(call: Call<ArrayList<WriterData>>, response: Response<ArrayList<WriterData>>) {
                for (list in response.body()!!){
                    arrayList.add(list)
                }
                writerTop100List.adapter = WriterListAdapter(requireContext(), arrayList)
            }

            override fun onFailure(call: Call<ArrayList<WriterData>>, t: Throwable) {
                Log.d("response : ", t.toString())
            }
        })
    }
}