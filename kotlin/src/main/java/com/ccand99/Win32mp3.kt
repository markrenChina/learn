package com.ccand99

import com.ccand99.Win32mp3.play

object Win32mp3 {

    init {
        System.load("E:\\cLionProject\\windows-mp3play\\lib\\windows_mp3play.dll")
    }

    external fun play(path : String)
}

fun main(){
    play("D:\\temp\\alarm_2.mp3");
    Thread.sleep(6000)
}