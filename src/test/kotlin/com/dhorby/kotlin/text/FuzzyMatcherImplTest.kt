package com.dhorby.kotlin.text

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FuzzyMatcherImplTest {

    val testList = mapOf(
            1 to "Oxford University",
            2 to "Cambridge University",
            3 to "Bristol University",
            4 to "Harvard College")

    val completeList = FuzzyMatcherImplTest::class.java.getResource("/data/entityids.csv").openStream().reader().readLines()


    var counter = 0;
    val mappedCompleteList = completeList.map { counter++ to it  }.toMap()

    @Test
    fun `the word 'ofxord university' should match to 'Oxford University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("oxford university")
        assertTrue(matchedValue == "University of Oxford", "Matched 'ofxord university' to $matchedValue")
    }

    @Test
    fun `the word 'ambridge univesirty' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("ambridge university")
        assertTrue(matchedValue == "University of Cambridge", "Matched 'ambridge university' to $matchedValue")
    }

    @Test
    fun `the word 'Cambridge University' should match to 'University of Cambridge'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("Cambridge University")
        assertTrue(matchedValue == "University of Cambridge", "Matched 'University of Cambridge' to $matchedValue")
    }

    @Test
    fun `the word 'Monfoyt' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("De Montfoyt")
        assertTrue(matchedValue == "De Montfort University Library Resources", "Matched 'De Montfoyt' to $matchedValue")
    }

}