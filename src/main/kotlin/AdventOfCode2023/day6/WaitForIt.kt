package AdventOfCode2023.day6

import AdventOfCode.Puzzle

data class Race(val raceTime: Long, val distance: Long)
class WaitForIt : Puzzle("2023", "6") {

    private fun getRaceTimesPart1(): List<Race> = inputData.map {
        // Split on spaces, ignore first item, transform tokens to integers
        it.split(Regex("\\s")).drop(1).map { it.trim().toLongOrNull() }.filterNotNull()
    }.zipWithNext().map {
        // Take the zipped lines (since we only have 2) and transform them into races.
        it.first.mapIndexed { index, i -> Race(i, it.second[index]) }
    }.flatten().also {
        println("The input is $it")
    }

    override fun partOne() {
        getRaceTimesPart1().map {
            // here we transform a race into a list of all the possible combinations that we can have
            (1..<it.raceTime).count { time -> (it.raceTime - time) * time > it.distance }
        }.reduce { acc, i -> acc * i }.also {
            println("The number of ways the record was beaten $it.")
        }
    }

    override fun partTwo() {
        listOf(Race(49787980, 298118510661181)).map {
            (1..<it.raceTime).count { time -> (it.raceTime - time) * time > it.distance }
        }.reduce { acc, i -> acc * i }.also {
            println("The number of ways the record was beaten $it.")
        }
    }
}