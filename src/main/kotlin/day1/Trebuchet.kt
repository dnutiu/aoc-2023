package day1

import java.io.File

class Trebuchet {
    fun partOne() {
        // read calibrations from file
        val calibrations = readCalibrationsFromFile()
        // print calibrations
        val calibrationData = calibrations.map {
            // replace non-digits with empty string
            it.replace(Regex("[^0-9]"), "")
        }.map {
            // convert to integer
            val firstDigit = it.first().toString().toInt()
            val secondDigit = it.last().toString().toInt()
            // return the sum of the two digits
            firstDigit * 10 + secondDigit
        }.sum()
        println("Part1: The calibration data is $calibrationData.")
    }

    fun partTwo() {
        // read calibrations from file
        val calibrations = readCalibrationsFromFile()
        // print calibrations
        val calibrationData = calibrations.map { it ->
            var digitsData = mapOf(
                "one" to "1",
                "two" to "2",
                "three" to "3",
                "four" to "4",
                "five" to "5",
                "six" to "6",
                "seven" to "7",
                "eight" to "8",
                "nine" to "9"
            )
            val currentBuffer: MutableList<String> = mutableListOf()
            val returnValue: MutableList<String> = mutableListOf()

            it.forEach {
                // If it's a digit we add it to return value
                if (it.isDigit()) {
                    returnValue.add(it.toString())
                    return@forEach
                }
                // We scan for numbers
                currentBuffer.add(it.toString())
                val currentBufferString = currentBuffer.joinToString(separator = "")
                for (key in digitsData.keys) {
                    if (currentBufferString.contains(key)) {
                        returnValue.add(digitsData[key].toString())
                        currentBuffer.clear()
                        // keep the last character
                        currentBuffer.add(it.toString())
                    }
                }
            }

            returnValue.joinToString(separator = "")
        }.map {
            it.replace(Regex("[^0-9]"), "")
        }.map {
            // convert to integer
            val firstDigit = it.first().toString().toInt()
            val secondDigit = it.last().toString().toInt()
            // return the sum of the two digits
            println(firstDigit * 10 + secondDigit)
            firstDigit * 10 + secondDigit
        }.sum()
        println("Part2: The calibration data is $calibrationData.")
    }

    private fun readCalibrationsFromFile(): List<String> {
        // read calibrations from resources file
        this.javaClass.getResourceAsStream("/day1/calibration.txt")?.bufferedReader()?.useLines { lines ->
            return lines.toList()
        }
        throw Exception("Could not read calibrations from file.")
    }

}