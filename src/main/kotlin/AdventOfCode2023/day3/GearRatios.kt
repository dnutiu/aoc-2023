package AdventOfCode2023.day3

import AdventOfCode.Puzzle

data class EnginePart(var number: Int, var row: Int, var startIndex: Int, var endIndex: Int)
data class Gear(var row: Int, val column: Int, var engineParts: MutableSet<EnginePart>)

class GearRatios : Puzzle("2023", "3") {
    private fun scanForEnginePats(data: List<String>): List<EnginePart> {
        val foundEngineParts: MutableList<EnginePart> = mutableListOf()

        // Step 1: Scan for index parts.
        data.forEachIndexed { rowIndex, row ->
            val numberDigits: MutableList<Char> = mutableListOf()
            var currentEnginePart = EnginePart(0, 0, 0, 0)
            var foundNumber = false
            row.forEachIndexed { colIndex, col ->
                // first digit
                if (col.isDigit() && !foundNumber) {
                    currentEnginePart.row = rowIndex
                    currentEnginePart.startIndex = colIndex
                    foundNumber = true
                    numberDigits.add(col)
                }
                // inside number
                else if (col.isDigit() && foundNumber) {
                    currentEnginePart.endIndex = colIndex
                    numberDigits.add(col)
                }
                // exit number
                else if (!col.isDigit() && foundNumber) {
                    currentEnginePart.number = numberDigits.joinToString(separator = "").toInt()
                    currentEnginePart.endIndex = colIndex - 1
                    foundEngineParts.add(currentEnginePart)
                    // reset
                    currentEnginePart = EnginePart(0, 0, 0, 0)
                    numberDigits.clear()
                    foundNumber = false
                }

                // Number is at edge
                if (foundNumber && colIndex == row.length - 1) {
                    currentEnginePart.number = numberDigits.joinToString(separator = "").toInt()
                    currentEnginePart.endIndex = colIndex
                    foundEngineParts.add(currentEnginePart)
                }
            }
        }
        return foundEngineParts
    }

    private fun filterForValidParts(data: List<String>, engineParts: List<EnginePart>): List<EnginePart> {
        val validEngineParts: MutableSet<EnginePart> = mutableSetOf()

        // compare each engine part diagonally with the given data to search for symbols.
        engineParts.forEach { enginePart ->
            for (i in enginePart.startIndex..enginePart.endIndex) {
                val movesOfXY = listOf(
                    Pair(i - 1, enginePart.row), // left
                    Pair(i - 1, enginePart.row - 1), // let top
                    Pair(i, enginePart.row - 1),// top
                    Pair(i + 1, enginePart.row - 1), // right top
                    Pair(i + 1, enginePart.row), // right
                    Pair(i + 1, enginePart.row + 1), // right bottom
                    Pair(i, enginePart.row + 1), // bottom
                    Pair(i - 1, enginePart.row + 1) // left bottom
                )
                movesOfXY.forEach movesForeach@{
                    if ((it.second >= 0 && it.second < data.size) && (it.first >= 0 && it.first < data[it.second].length)) {
                        val symbol = data[it.second][it.first]
                        if (!symbol.isDigit() && symbol != '.') {
                            validEngineParts.add(enginePart)
                            return@forEach
                        }
                    }
                }
            }
        }

        return validEngineParts.toList()
    }

    override fun partOne() {
        val foundEngineParts: List<EnginePart> = scanForEnginePats(inputData)

        foundEngineParts.forEach { it ->
            println(it)
        }

        val validEngineParts = filterForValidParts(inputData, foundEngineParts)

        val enginePartsSum = validEngineParts.map { it.number }.sum()
        println("The engine parts sum is $enginePartsSum")
    }

    override fun partTwo() {
        val foundEngineParts: List<EnginePart> = scanForEnginePats(inputData)
        val gears: MutableList<Gear> = mutableListOf()

        inputData.forEachIndexed { row, rowElement ->
            rowElement.forEachIndexed { col, colElement ->
                if (colElement == '*') {
                    val enginePartsForGear: MutableSet<EnginePart> = mutableSetOf()
                    val movesOfXY = listOf(
                        Pair(col - 1, row), // left
                        Pair(col - 1, row - 1), // let top
                        Pair(col, row - 1),// top
                        Pair(col + 1, row - 1), // right top
                        Pair(col + 1, row), // right
                        Pair(col + 1, row + 1), // right bottom
                        Pair(col, row + 1), // bottom
                        Pair(col - 1, row + 1) // left bottom
                    )
                    movesOfXY.forEach {
                        if ((it.second >= 0 && it.second < inputData.size) && (it.first >= 0 && it.first < inputData[it.second].length)) {
                            val symbol = inputData[it.second][it.first]
                            if (symbol.isDigit()) {
                                // search engine part
                                foundEngineParts.forEach { part ->
                                    if (part.row == it.second && (it.first >= part.startIndex && it.first <= part.endIndex)) {
                                        enginePartsForGear.add(part)
                                    }
                                }
                            }
                        }
                    }
                    gears.add(Gear(row, col, enginePartsForGear))
                }
            }
        }

        gears.filter { it.engineParts.size == 2 }.map {
            it.engineParts.map { it.number }.reduce { acc, i -> acc * i }
        }.sum().let {
            println("The gear ratio is $it")
        }
    }
}