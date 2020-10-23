package com.vincent.shadowsocksdemo.model

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Vincent on 2020/4/10.
 */
class testSolution {

    @Test
    fun alphabetBoardPath() {
        val target = "adult"

        if (isLengthIllegal(target) || isNotAllLowercase(target)) {
            throw IllegalArgumentException()
        }

        val moves = StringBuilder()

        var currentRow = 0
        var currentCol = 0

        for (char in target.toCharArray()) {
            val targetRow = (char - 'a') / 5
            val targetCol = (char - 'a') % 5

            addMoves(targetRow, currentRow, targetCol, currentCol, moves)

            currentRow = targetRow
            currentCol = targetCol
        }

        assert(true)
    }

    private fun addMoves(
        targetRow: Int,
        currentRow: Int,
        targetCol: Int,
        currentCol: Int,
        moves: StringBuilder) {
        var currentRow = currentRow
        var currentCol = currentCol

        while (targetRow < currentRow) {
            moves.append('U')
            currentRow--
        }

        while (targetCol < currentCol) {
            moves.append('L')
            currentCol--
        }

        while (targetRow > currentRow) {
            moves.append('D')
            currentRow++
        }

        while (targetCol > currentCol) {
            moves.append('R')
            currentCol++
        }

        moves.append('!')
    }

    private fun isLengthIllegal(target: String): Boolean {
        return target.isEmpty() || target.length > 100
    }

    private fun isNotAllLowercase(target: String): Boolean {
        return !Regex("[a-z]+").matches(target)
    }
}