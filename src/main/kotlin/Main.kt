import AdventOfCode2023.day6.WaitForIt
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val t = WaitForIt()
    val time = measureTimeMillis {
//        t.partOne()
         t.partTwo()
    }
    println("Took $time ms.")
}