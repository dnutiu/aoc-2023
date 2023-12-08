import AdventOfCode2023.day8.HauntedWasteland
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val t = HauntedWasteland()
    val time = measureTimeMillis {
        t.partOne()
        // t.partTwo()
    }
    println("Took $time ms.")
}