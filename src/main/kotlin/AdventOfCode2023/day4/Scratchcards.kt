package AdventOfCode2023.day4

import AdventOfCode.Puzzle

class Scratchcards: Puzzle("2023", "4") {
    override fun partOne() {
        var points = 0
        inputData.forEach {
            var sum = 0
            val parts = it.split("|")
            val winningNumbers = parts[0].split(":" )[1].split(Regex("\\s"))
            // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            winningNumbers.filter { it != "" }.forEach {
                var cleanParts = parts[1].split(Regex("\\s")).filter { it != "" }.toSet()
                if (cleanParts.contains(it)) {
                    sum += 1
                }
            }
            points += Math.floor(Math.pow(2.0, sum.toDouble() - 1)).toInt()
        }

        println("The number of points: $points")
    }

    override fun partTwo() {
        TODO("Not yet implemented")
    }
}