package com.dhorby.kotlin.text

import org.apache.commons.text.similarity.LevenshteinDistance


class FuzzyMatcherImpl(val checkList:List<String>) : FuzzyMatcher {
    override fun fuzzyMatch(checkText: String): String {
        val checkMap = checkList.map { text -> LevenshteinDistance.getDefaultInstance().apply(text, checkText)!! to text }
        var mostLikelyMatch = checkMap.minBy { it ->  it.first }
        return mostLikelyMatch!!.second
    }
}