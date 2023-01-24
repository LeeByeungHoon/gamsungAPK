package com.hannip.myapplication.ui.module

import java.util.*

data class ContentData(val title:String, val Content:String, val Like:Long, val writer:String)
data class WriterData(val userId:String, val userNickname:String, val follower:Long)
