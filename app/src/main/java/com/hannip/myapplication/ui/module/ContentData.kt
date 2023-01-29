package com.hannip.myapplication.ui.module

import java.time.LocalDateTime
import java.util.*

data class ContentData(val userNickname:String, val content:String, val likeCount:Long, val createAt:LocalDateTime)
data class WriterData(val userNickname:String, val follower:Long, val userIntroduce:String)
