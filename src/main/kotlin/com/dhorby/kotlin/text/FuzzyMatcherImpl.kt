package com.dhorby.kotlin.text

import org.apache.commons.text.similarity.FuzzyScore
import org.apache.commons.text.similarity.LevenshteinDistance
import java.util.*


class FuzzyMatcherImpl(val checkList: Map<Int, String>) : FuzzyMatcher {

    val cleanList: Map<Int, String> = checkList.map { it.key to it.value.split(" ").filter { !stopWordList.contains(it) }.joinToString(" ") }.toMap()

    companion object {
        val stopWordList = listOf("University", "College", "of")

    }
    override fun levenshteinDistance(checkText: String): String? {
        val cleanedText = checkText.split(" ").filter { !stopWordList.contains(it) }.joinToString(" ")
        println("Cleaned text " + cleanedText)
        val checkMap = cleanList.map { text -> LevenshteinDistance.getDefaultInstance().apply(text.value, cleanedText)!! to text }.toMap()
        checkMap.forEach {println("${it.key}:${it.value}")}
        val mostLikelyMatch = checkMap.minBy { it ->  it.key }
        val matchedMapValue = checkList.get(mostLikelyMatch!!.value.key)
        return matchedMapValue
    }

    fun fuzzyScore(checkText: String): String? {
        val fuzzyScore = FuzzyScore(Locale.ENGLISH)
        val checkMap = cleanList.map { text -> fuzzyScore.fuzzyScore(text.value, checkText)!! to text }
        val mostLikelyMatch = checkMap.minBy { it ->  it.first }
        val matchedMapValue = checkList.get(mostLikelyMatch!!.first)
        return matchedMapValue
    }
}