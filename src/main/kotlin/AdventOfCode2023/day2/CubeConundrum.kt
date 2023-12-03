package AdventOfCode2023.day2

import AdventOfCode.Puzzle
import java.util.Scanner

class CubeConundrum : Puzzle("2023", "2") {

    private fun getGameList(): List<Game> {
        return inputData.map {
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
                cubes.forEach cubeForEach@{ cubeSet ->
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
        /*
            For each game, find the minimum set of cubes that must have been present.
            What is the sum of the power of these sets?
         */
        val games = getGameList();
        val sum = games.map {
            var gamePower = 0
            var minimumPerGame = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )
            it.gameData?.forEach { gameData ->
                val cubes = gameData.split(",")
                cubes.forEach cubeForEach@{ cubeSet ->
                    val cubeData = cubeSet.trim().split(" ")
                    minimumPerGame[cubeData[1]] =
                        Math.max(minimumPerGame[cubeData[1]]!!, cubeData[0].toInt())
                }
            }

            gamePower = minimumPerGame["red"]!! * minimumPerGame["green"]!! * minimumPerGame["blue"]!!

            gamePower
        }.sum()
        println("The sum of the game power is $sum.")
    }
}