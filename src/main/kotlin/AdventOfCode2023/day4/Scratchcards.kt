package AdventOfCode2023.day4

import AdventOfCode.Puzzle

class Scratchcards: Puzzle("2023", "4") {
    override fun partOne() {
        var points = 0
        inputData.forEach {
            var sum = 0
            val parts = it.split("|")
            val winningNumbers = parts[0].split(":" )[1].split(Regex("\\s"))
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
        inputData.forEachIndexed { i, it ->
            println(getWinningNumbersForCard(it))
        }
    }

    private fun getWinningNumbersForCard(
        card: String,
    ): Int {
        val parts = card.split("|")
        val cleanParts = parts[1].split(Regex("\\s")).filter { it != "" }.toSet()
        val winningNumbers = parts[0].split(":")[1].split(Regex("\\s")).filter { it != "" }.toSet()
        return winningNumbers.filter { number -> cleanParts.contains(number) }.count()
    }
}