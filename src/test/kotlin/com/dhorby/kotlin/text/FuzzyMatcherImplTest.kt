package com.dhorby.kotlin.text

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FuzzyMatcherImplTest {

    val completeList = FuzzyMatcherImplTest::class.java.getResource("/data/entityids.csv").openStream().reader().readLines()


    var counter = 0;
    val mappedCompleteList = completeList.map { counter++ to it }.toMap()


    @Test
    fun `the word 'ofxord university' should match to 'Oxford University'`() {
        val expectedValue = FuzzyMatch("University of Oxford", "oxford university")

        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValues = fuzzyMatcherImpl.fuzzyAndLevensteinScore("oxford university")
        assertTrue(matchedValues.contains(expectedValue), "Matched 'ofxord university' to $matchedValues")
    }

    @Test
    fun `the word 'ambridge university' should match to 'Cambridge University'`() {
        val expectedValue = FuzzyMatch("University of Cambridge", "ambridge university")
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("ambridge university")
        assertTrue(matchedValue.contains(expectedValue), "Matched 'ambridge university' to $matchedValue")
    }

    @Test
    fun `the word 'Cambridge University' should match to 'University of Cambridge'`() {
        val expectedValue = FuzzyMatch("University of Cambridge", "Cambridge University")
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("Cambridge University")
        assertTrue(matchedValue.contains(expectedValue), "Matched 'University of Cambridge' to $matchedValue")
    }

    @Test
    fun `the word 'Monfoyt' should match to 'De Montfort University Library Resources'`() {
        val expectedValue = FuzzyMatch("De Montfort University Library Resources", "De Montfoyt")
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("De Montfoyt")
        assertTrue(matchedValue.contains(expectedValue), "Matched 'De Montfoyt' to $matchedValue")
    }

    @Test
    fun `the word 'Technical University Clausthal' should match to 'Technische Universität Clausthal'`() {
        val expectedValue = FuzzyMatch("Technische Universität Clausthal", "Technical University Clausthal")
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        val matchedValue = fuzzyMatcherImpl.fuzzyAndLevensteinScore("Technical University Clausthal")
        assertTrue(matchedValue.contains(expectedValue), "Matched 'Technical University Clausthal' to $matchedValue")
    }

}
