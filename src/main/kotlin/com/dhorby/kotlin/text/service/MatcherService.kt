package com.dhorby.kotlin.text.service

import com.dhorby.kotlin.text.FuzzyMatch
import com.dhorby.kotlin.text.FuzzyMatcherImpl

class MatcherService() {

    companion object {
        var counter = 0;
        val completeList = MatcherService::class.java.getResource("/data/entityids.csv").openStream().reader().readLines()
        val mappedCompleteList = completeList.map { counter++ to it  }.toMap()
    }

    fun match(institutionName:String): List<FuzzyMatch> {
        val fuzzyMatcherImpl = FuzzyMatcherImpl(mappedCompleteList)
        return fuzzyMatcherImpl.fuzzyAndLevensteinScore(institutionName)
    }
}