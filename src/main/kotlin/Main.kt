import AdventOfCode2023.day4.Scratchcards
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val t = Scratchcards()
    // t.partOne()
    val time = measureTimeMillis {
        t.partTwo()
    }
    println("Took $time ms.")
}