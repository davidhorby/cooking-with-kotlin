package com.dhorby.kotlin.text

import org.apache.commons.text.similarity.FuzzyScore
import org.apache.commons.text.similarity.LevenshteinDistance
import java.util.*

data class FuzzyMatch(val keyVal:Int, val origVal:String, val checkText:String) {
    val fuzzyScoreer = FuzzyScore(Locale.ENGLISH)
    val fuzzyScoreVal:Int = fuzzyScoreer.fuzzyScore(origVal.toLowerCase(), checkText.toLowerCase())
    val levenshteinDistance:Int =  LevenshteinDistance.getDefaultInstance().apply(origVal.toLowerCase(), checkText.toLowerCase())
    override fun toString(): String {
        return "${keyVal}, $origVal, $checkText, $fuzzyScoreVal, $levenshteinDistance"
    }
}


class FuzzyMatcherImpl(val checkList: Map<Int, String>) : FuzzyMatcher {

    val cleanList: Map<Int, String> = checkList.map { it.key to it.value.split("\\W+".toRegex()).filter { !stopWordList.contains(it.toLowerCase()) }.joinToString(" ") }.toMap()


    companion object {
        val stopWordList = listOf("university", "college", "school", "institute")
    }

//    fun sumLevenshteinDistance(testString: String, compareString: String): Int {
//        val testList = testString.split("\\W+".toRegex())
//        val compareList = compareString.split("\\W+".toRegex())
//        return testList.flatMap {
//            compareList.map { compareWord -> getValidLevenshteinDistance(it, compareWord) }
//        }.sum()
//    }

    fun stopWordFilter(checkText:String): List<String> {
        return stopWordList.filter { checkText.toLowerCase().contains(it) }
    }

    fun sumLevenshteinDistance(testString: String, compareString: String): Int {
        return getValidLevenshteinDistance( testString, compareString)
    }


    fun getValidLevenshteinDistance(testWord:String,compareWord:String):Int  {
        val result = LevenshteinDistance.getDefaultInstance().apply(testWord.toLowerCase(), compareWord.toLowerCase())
        println("Comparing " + testWord + " to " + compareWord + " = " + result )
        return result

//        val final = if (result < testWord.length && result < compareWord.length)
//             result
//        else  0
//        println("Comparing " + testWord + " to " + compareWord + " = " + result + " return= " + final)
//        return final
    }

    override fun levenshteinDistance(checkText: String): String? {
        val cleanedText = checkText.split(" ").filter { !stopWordList.contains(it.toLowerCase()) }.joinToString(" ")
        val checkMap = cleanList.map { it.key to sumLevenshteinDistance(it.value, cleanedText) }.toMap()
        val mostLikelyMatch = checkMap.minBy { it ->  it.value }
        val matchedMapValue = checkList.get(mostLikelyMatch!!.key)
        return matchedMapValue
    }

    fun fuzzyScore(checkText: String): String? {
        val fuzzyScore = FuzzyScore(Locale.ENGLISH)
        val checkMap = checkList.map { it.key to fuzzyScore.fuzzyScore(it.value.toLowerCase(), checkText.toLowerCase())}.toMap()
        val topResults = checkMap.
                filter { it.value > 5 }
        val finalMap = topResults.map { checkList.get(it.key) to it.value }
        finalMap.forEach { println(it.first + ":" + it.second) }
        val mostLikelyMatch = checkMap.maxBy { it ->  it.value }
        val matchedMapValue = checkList.get(mostLikelyMatch!!.key)
        return matchedMapValue
    }

    fun fuzzyAndLevensteinScore(checkText: String):String {

        val fuzzyChecKList = checkList.map { FuzzyMatch(it.key, it.value, checkText) }

        val filterList = stopWordFilter(checkText)
        val realCheckList = if (filterList.size > 0) {
            fuzzyChecKList.filter { filterList.filter { filterval -> it.origVal.contains(filterval) }.size == 0 }
        } else {
            fuzzyChecKList
        }



        val firstFilter = realCheckList.filter { it.fuzzyScoreVal > 6 }
        firstFilter.forEach { println(it) }
//        firstFilter.filter { it.levenshteinDistance < 20 }.forEach { println(it) }
        val mostLikelyMatch = firstFilter.maxBy { it.fuzzyScoreVal }
        return mostLikelyMatch?.origVal.orEmpty()
    }
}