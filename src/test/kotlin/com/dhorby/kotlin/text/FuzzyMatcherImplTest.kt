package com.dhorby.kotlin.text

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FuzzyMatcherImplTest {


    val testList = mapOf(
            1 to "Oxford University",
            2 to "Cambridge University",
            3 to "Bristol University",
            4 to "Harvard College")

    @Test
    fun `the word 'ofxord' should match to 'Oxford University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.levenshteinDistance("ofxord")
        assertTrue(matchedValue == "Oxford University", "Matched 'ofxord' to $matchedValue")
    }

    @Test
    fun `the word 'ambriddeg univesirty' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.levenshteinDistance("ambriddeg univesirty")
        assertTrue(matchedValue == "Cambridge University", "Matched 'ambriddeg univesirty' to $matchedValue")
    }

    @Test
    fun `the word 'University of Cambridge' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.levenshteinDistance("University of Cambridge")
        assertTrue(matchedValue == "Cambridge University", "Matched 'University of Cambridge' to $matchedValue")
    }
}