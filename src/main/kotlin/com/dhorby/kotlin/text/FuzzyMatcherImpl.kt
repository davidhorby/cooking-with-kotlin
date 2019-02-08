package com.dhorby.kotlin.text

import org.apache.commons.text.similarity.FuzzyScore
import org.apache.commons.text.similarity.LevenshteinDistance
import java.util.*

data class FuzzyMatch(val origVal: String, val checkText: String) {
    val fuzzyScoreer = FuzzyScore(Locale.ENGLISH)
    val fuzzyScoreVal:Int = fuzzyScoreer.fuzzyScore(origVal.toLowerCase().removeFilterWords(), checkText.toLowerCase())
    val levenshteinDistance:Int =
            LevenshteinDistance.getDefaultInstance().apply(origVal.toLowerCase().removeFilterWords(), checkText.toLowerCase())
    override fun toString(): String {
        return "$origVal, $checkText, $fuzzyScoreVal, $levenshteinDistance"
    }
}

inline fun String.removeFilterWords():String {
    FuzzyMatcherImpl.stopWordList.forEach {
        this.replace(it, " ")
    }
    return this
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

//    override fun funLevenshteinDistance(checkText: String): String? {
//        val cleanedText = checkText.split(" ").filter { !stopWordList.contains(it.toLowerCase()) }.joinToString(" ")
//        val checkMap = cleanList.map { it.key to sumLevenshteinDistance(it.value, cleanedText) }.toMap()
//        val mostLikelyMatch = checkMap.minBy { it ->  it.value }
//        val matchedMapValue = checkList.get(mostLikelyMatch!!.key)
//        return matchedMapValue
//    }

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

    fun fuzzyAndLevensteinScore(checkText: String): List<FuzzyMatch> {

        val fuzzyChecKList = checkList.map { FuzzyMatch(it.value, checkText) }

        val filterList = stopWordFilter(checkText)
        val realCheckList = if (filterList.size > 0) {

            // Clean the original text
            val cleanWord = filterList.map { checkText.replace(it, "") }.joinToString(" ")

            val result = fuzzyChecKList
                    .filter { checkForFilterWord(filterList, it.origVal) }
//            result.forEach {println(it)}
            result
        } else {
            fuzzyChecKList
        }


        val firstFilter = fuzzyChecKList.filter { it.fuzzyScoreVal > 10 }
//        firstFilter.forEach { println(it) }
        firstFilter.filter { it.levenshteinDistance < 20 }.forEach { println(it) }
        val mostLikelyMatch = firstFilter.maxBy { it.fuzzyScoreVal }
        return firstFilter
    }



    fun checkForFilterWord(filterList:List<String>, word:String):Boolean {
        val test = filterList.map { checkWord(word, it) }.toList()
//        println("returning ${test.any(true)} for $word")
        return test.any{ it }
    }

    private fun checkWord(word: String, filterWord: String):Boolean  {
        return word.toLowerCase().contains(filterWord.toLowerCase())
    }
}