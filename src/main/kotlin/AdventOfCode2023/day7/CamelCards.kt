package AdventOfCode2023.day7

import AdventOfCode.Puzzle

data class Card(val data: String, val bid: Int) {
    constructor(data: List<String>) : this(data[0], data[1].toInt())

    /*
    Every hand is exactly one type. From strongest to weakest, they are:
    Five of a kind, where all five cards have the same label: AAAAA
    Four of a kind, where four cards have the same label and one card has a different label: AA8AA
    Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
    Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
    Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
    One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
    High card, where all cards' labels are distinct: 23456
     */
    operator fun compareTo(second: Card?): Int {
        val cardValues = listOf("2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A")
        if (second == null) {
            return 1
        }

        val thisGroups = this.data.groupBy { it }.map { it.value.size }.sortedDescending()
        val secondGroups = second.data.groupBy { it }.map { it.value.size }.sortedDescending()

        for (i in thisGroups.indices) {
            if (thisGroups[i] > secondGroups[i]) {
                return 1
            } else if (thisGroups[i] < secondGroups[i]) {
                return -1
            }
        }

        // Compare high cards if the TYPE of hands are the same
        for (i in this.data.indices) {
            val firstCardPower = cardValues.indexOf<String>(element = this.data[i].toString())
            val secondCardPower = cardValues.indexOf<String>(element = second.data[i].toString())
            if (firstCardPower>secondCardPower) {
                return 1
            } else if (firstCardPower < secondCardPower) {
                return -1
            }
        }
        return 0
    }
}

class CamelCards : Puzzle("2023", "7") {
    override fun partOne() {
        inputData.map { Card(it.split(Regex("\\s"))) }.sortedWith(Card::compareTo).mapIndexed { i, card ->
            card.bid * (i + 1)
        }.sum().also { println("The sum is $it") }
    }

    override fun partTwo() {
        TODO("Not yet implemented")
    }
}