package AdventOfCode2023.day8

import AdventOfCode.Puzzle

data class HauntedWastelandMap(val directions: String, val map: Map<String, Pair<String, String>>)

class HauntedWasteland : Puzzle("2023", "8") {

    private fun parseInput(): HauntedWastelandMap {
        val hauntedWastelandMap: MutableMap<String, Pair<String, String>> = mutableMapOf()
        inputData.drop(1).filterNot(String::isBlank).forEach {
            val parts = it.split("=", ",")
            hauntedWastelandMap[parts[0].trim()] = Pair(parts[1].trim('(', ' '), parts[2].trim(')', ' '))
        }

        return HauntedWastelandMap(inputData[0], hauntedWastelandMap)
    }

    override fun partOne() {
        val desertMap = parseInput().also { println("The map is $it") }
        var currentLocation = "AAA"
        var steps = 0
        game@ while (true) {
            for (direction in desertMap.directions) {
                when (direction) {
                    'L' -> currentLocation = desertMap.map[currentLocation]!!.first
                    'R' ->  currentLocation = desertMap.map[currentLocation]!!.second
                }
                steps += 1
                if (currentLocation == "ZZZ") {
                    break@game
                }
            }
        }
        println("Steps taken $steps")
    }

    override fun partTwo() {
        TODO("Not yet implemented")
    }
}