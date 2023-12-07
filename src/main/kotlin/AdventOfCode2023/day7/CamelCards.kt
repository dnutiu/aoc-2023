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

private fun Map<Char, Int>.keyForMaxValue(): Char {
    var maxValue = entries.first().value
    var maxKey = entries.first().key

    for (entry in entries) {
        // skip joker
        if (entry.value > maxValue) {
            maxValue = entry.value
            maxKey = entry.key
        }
    }
    return maxKey
}

data class CardWithJoker(val data: String, val bid: Int) {
    constructor(data: List<String>) : this(data[0], data[1].toInt())

    /*
    32T3K is still the only one pair; it doesn't contain any jokers, so its strength doesn't increase.
    KK677 is now the only two pair, making it the second-weakest hand.
    T55J5, KTJJT, and QQQJA are now all four of a kind! T55J5 gets rank 3, QQQJA gets rank 4, and KTJJT gets rank 5.
     */
    operator fun compareTo(second: CardWithJoker?): Int {
        val cardValues = listOf("", "J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A")
        if (second == null) {
            return 1
        }

        // ADD joker to stronger group in map
        val thisGroups = this.data.groupBy { it }.let {
            val newMap = mutableMapOf<Char, Int>()
            var jokerFactor = 0
            for (entry in it.entries) {
                if (entry.key == 'J') {
                    jokerFactor = entry.value.count()
                    continue
                }
                newMap[entry.key] = entry.value.count()
            }
            // we have jokers only
            if (newMap.isEmpty()) {
                newMap['J'] = 5
                return@let newMap
            }
            val maxEntry: Char = newMap.keyForMaxValue()
            newMap[maxEntry] = newMap[maxEntry]?.plus(jokerFactor)!!
            newMap
        }.map { it.value }.sortedDescending()
        val secondGroups = second.data.groupBy { it }.let {
            val newMap = mutableMapOf<Char, Int>()
            var jokerFactor = 0
            for (entry in it.entries) {
                if (entry.key == 'J') {
                    jokerFactor = entry.value.count()
                    continue
                }
                newMap[entry.key] = entry.value.count()
            }
            // we have jokers only
            if (newMap.isEmpty()) {
                newMap['J'] = 5
                return@let newMap
            }
            val maxEntry: Char = newMap.keyForMaxValue()
            newMap[maxEntry] = newMap[maxEntry]?.plus(jokerFactor)!!
            newMap
        }.map { it.value }.sortedDescending()

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
        inputData.map { CardWithJoker(it.split(Regex("\\s"))) }.sortedWith(CardWithJoker::compareTo).mapIndexed { i, card ->
            card.bid * (i + 1)
        }.sum().also { println("The sum is $it") }
    }
}