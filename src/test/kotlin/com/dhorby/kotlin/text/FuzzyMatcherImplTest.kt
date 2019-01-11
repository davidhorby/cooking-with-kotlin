package com.dhorby.kotlin.text

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FuzzyMatcherImplTest {


    val testList = mutableListOf(
            "Oxford University",
            "Cambridge University",
            "Bristol University")

    @Test
    fun `the word 'ofxord' should match to 'Oxford University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.fuzzyMatch("ofxord")
        assertTrue(matchedValue == "Oxford University")
    }

    @Test
    fun `the word 'ambriddeg univesirty' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.fuzzyMatch("ambriddeg univesirty")
        assertTrue(matchedValue == "Cambridge University")
    }

    @Test
    fun `the word 'University of Cambridge' should match to 'Cambridge University'`() {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(testList)
        val matchedValue = fuzzyMatcherImpl.fuzzyMatch("University of Cambridge")
        assertTrue(matchedValue == "Cambridge University", "Matched Cambridge University to $matchedValue")
    }
}