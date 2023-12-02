package adventOfCode

interface Puzzle {

    // Part one of the AoC puzzle.
    fun partOne()

    // Part two of the AoC puzzle.
    fun partTwo()

    fun readInputFromFile(dayNumber: String): List<String> {
        // read calibrations from resources file
        this.javaClass.getResourceAsStream("/aoc2023/input_day$dayNumber.txt")?.bufferedReader()?.useLines { lines ->
            return lines.toList()
        }
        throw Exception("Could not read calibrations from file.")
    }
}