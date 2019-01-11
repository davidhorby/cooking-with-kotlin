package com.dhorby.kotlin.text

interface FuzzyMatcher {
    fun levenshteinDistance(text:String):String?
}