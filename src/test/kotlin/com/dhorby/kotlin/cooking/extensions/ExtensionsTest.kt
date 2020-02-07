package com.dhorby.kotlin.cooking.extensions


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExtensionsTest {

    @Test
    internal fun `check that round up works`() {
        val testDouble = 0.565
        assertEquals(0.57, testDouble.round())
    }

    @Test
    internal fun `check that round up works 2`() {
        val testDouble = 0.563
        assertEquals(0.56, testDouble.round())
    }
}
