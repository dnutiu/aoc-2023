import AdventOfCode2023.day7.CamelCards
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val t = CamelCards()
    val time = measureTimeMillis {
         t.partOne()
         // t.partTwo()
    }
    println("Took $time ms.")
}