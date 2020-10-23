package com.vincent.shadowsocksdemo.model

/**
 * Created by Vincent on 2020/4/10.
 */
class Solution {

    @Throws(IllegalArgumentException::class)
    fun alphabetBoardPath(target: String): String {
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

        return moves.toString()
    }

    private fun addMoves(
        targetRow: Int,
        currentRow: Int,
        targetCol: Int,
        currentCol: Int,
        moves: StringBuilder) {
        var row = currentRow
        var col = currentCol

        while (targetRow < row) {
            moves.append('U')
            row--
        }

        while (targetCol < col) {
            moves.append('L')
            col--
        }

        while (targetRow > row) {
            moves.append('D')
            row++
        }

        while (targetCol > col) {
            moves.append('R')
            col++
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