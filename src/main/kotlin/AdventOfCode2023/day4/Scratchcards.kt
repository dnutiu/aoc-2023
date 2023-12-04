package AdventOfCode2023.day4

import AdventOfCode.Puzzle
import kotlin.collections.ArrayDeque

data class Card(val number: Int, val wonCards: Int)

class Scratchcards : Puzzle("2023", "4") {
    override fun partOne() {
        var points = 0
        inputData.forEach {
            var sum = 0
            val parts = it.split("|")
            val winningNumbers = parts[0].split(":")[1].split(Regex("\\s"))
            winningNumbers.filter { it != "" }.forEach {
                val cleanParts = parts[1].split(Regex("\\s")).filter { it != "" }.toSet()
                if (cleanParts.contains(it)) {
                    sum += 1
                }
            }
            points += Math.floor(Math.pow(2.0, sum.toDouble() - 1)).toInt()
        }

        println("The number of points: $points")
    }

    override fun partTwo() {
        var cardsTotal = 0
        val cardsList = inputData.mapIndexed { index, s -> Card(index + 1, getWinningNumbersForCard(s)) }.toList()

        val stack = ArrayDeque(cardsList)
        while (!stack.isEmpty()) {
            val card = stack.removeFirst()
            if (card.wonCards > 0) {
                val wonCardsList = cardsList.slice(card.number..<card.number+card.wonCards)
                wonCardsList.forEach { stack.addLast(it) }
            }
            cardsTotal += 1
        }
        println("Total cards seen $cardsTotal")
    }

    private fun getWinningNumbersForCard(
        card: String,
    ): Int {
        val parts = card.split("|")
        val cleanParts = parts[1].split(Regex("\\s")).filter { it != "" }.toSet()
        val winningNumbers = parts[0].split(":")[1].split(Regex("\\s")).filter { it != "" }.toSet()
        return winningNumbers.count { number -> cleanParts.contains(number) }
    }
}