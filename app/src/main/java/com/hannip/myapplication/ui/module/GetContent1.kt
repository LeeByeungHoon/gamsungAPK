package com.hannip.myapplication.ui.module

import java.util.*

data class ContentList(val title:String, val Content:String, val Like:Long, val writer:String)
class GetContent1 {
    val contentList = listOf<ContentList>(ContentList("제목1", "내용1",100L,"작성자1"),ContentList("제목2", "내용2",100,"작성자2"),ContentList("제목3", "내용3",100L,"작성자3"),ContentList("제목4", "내용4",100L,"작성자4"))

    public fun getContent1Data(i:Int) : ContentList{
        return contentList[i%contentList.size]
    }
}