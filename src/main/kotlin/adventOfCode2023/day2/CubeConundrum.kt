package adventOfCode2023.day2

import adventOfCode.Puzzle
import java.util.Scanner

class CubeConundrum : Puzzle {

    private fun getGameList(): List<Game> {
        val input = this.readInputFromFile("2")

        return input.map {
            val game = Game(0, null)
            val line = it.split(":", limit = 2)
            // Get game id
            val scanner = Scanner(line[0])
            scanner.next() // skip Game
            game.id = scanner.nextInt()
            // Get cubes numbers.
            game.gameData = line[1].split(";")
            return@map game
        }
    }

    override fun partOne() {
        /*
        The Elf would first like to know which games would have been possible if the bag
        contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
         */
        val games = getGameList();
        var gameIdsSum = 0
        val gameMaxConstraints = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        games.forEach {
            println(it)
            var gamePossible = true
            it.gameData?.forEach { gameData ->
                val cubes = gameData.split(",")
                cubes.forEach cubeForEach@ { cubeSet ->
                    val cubeData = cubeSet.trim().split(" ")
                    if (cubeData[0].toInt() > gameMaxConstraints.getOrDefault(cubeData[1], Int.MAX_VALUE)) {
                        gamePossible = false
                        return@cubeForEach
                    }
                }
            }
            if (gamePossible) {
                gameIdsSum += it.id
            }
        }
        println("The sum of the game ids is $gameIdsSum.")
    }

    override fun partTwo() {
        TODO("Not yet implemented")
    }
}