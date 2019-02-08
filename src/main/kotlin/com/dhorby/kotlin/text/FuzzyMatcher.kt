package com.dhorby.kotlin.text

interface FuzzyMatcher {
//    fun funLevenshteinDistance(text:String):String?
}

sealed class Matched() {
    class SuccessfulMatch():Matched()
    class UnsuccessfulMatch():Matched()
}