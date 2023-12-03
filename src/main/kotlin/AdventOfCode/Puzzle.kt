package AdventOfCode

abstract class Puzzle(private val year: String, private val day: String) {
    protected val inputData = this.readInputFromFile()

    // Part one of the AoC puzzle.
    abstract fun partOne()

    // Part two of the AoC puzzle.
    abstract fun partTwo()

    fun readInputFromFile(): List<String> {
        // read calibrations from resources file
        this.javaClass.getResourceAsStream("/aoc$year/input_day$day.txt")?.bufferedReader()?.useLines { lines ->
            return lines.toList()
        }
        throw Exception("Could not read calibrations from file.")
    }
}