import java.util.*

class Card(val rank: Rank, val suit: Suit) {
    override fun toString(): String {
        return "${rankInRussian()} ${suitInRussian()}"
    }

    private fun rankInRussian(): String {
        return when (rank) {
            Rank.SIX -> "Шестерка"
            Rank.SEVEN -> "Семерка"
            Rank.EIGHT -> "Восьмерка"
            Rank.NINE -> "Девятка"
            Rank.TEN -> "Десятка"
            Rank.JACK -> "Валет"
            Rank.QUEEN -> "Дама"
            Rank.KING -> "Король"
            Rank.ACE -> "Туз"
        }
    }

    private fun suitInRussian(): String {
        return when (suit) {
            Suit.HEARTS -> "Черви"
            Suit.DIAMONDS -> "Бубны"
            Suit.CLUBS -> "Трефы"
            Suit.SPADES -> "Пики"
        }
    }
}

enum class Rank(val value: Int) {
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14)
}

enum class Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

class Player(val name: String) {
    val hand = mutableListOf<Card>()

    fun takeCard(card: Card) {
        hand.add(card)
    }

    fun playCard(index: Int): Card {
        return hand.removeAt(index)
    }

    override fun toString(): String {
        val cardsString = hand.withIndex()
            .joinToString("\n") { (index, card) -> "$index: ${card.toString()}" }
        return "$name:\n$cardsString"
    }
}

class Game {
    private val deck = Deck()
    private val player1 = Player("Игрок 1")
    private val player2 = Player("Игрок 2")

    init {
        for (i in 0 until 6) {
            player1.takeCard(deck.drawCard()!!)
            player2.takeCard(deck.drawCard()!!)
        }
    }

    fun play() {
        while (player1.hand.isNotEmpty() && player2.hand.isNotEmpty()) {
            println(player1)
            println(player2)
            println("Осталось карт в колоде: ${deck.cards.size}")

            // Ход игрока 1
            val attackIndex = player1.chooseCardIndex()
            val attackingCard = player1.playCard(attackIndex)

            // Ход игрока 2 (защита)
            val defendIndex = player2.chooseCardIndex()
            val defendingCard = player2.playCard(defendIndex)

            // Логика определения победителя хода
            if (attackingCard.rank.value > defendingCard.rank.value) {
                println("${player2.name} защитился: ${defendingCard}")
            } else {
                println("${player2.name} не смог защититься и получает карту: ${defendingCard}")
                player1.takeCard(defendingCard)
            }
        }

        if (player1.hand.isEmpty()) {
            println("${player1.name} выиграл!")
        } else {
            println("${player2.name} выиграл!")
        }
    }
}

class Deck {
    val cards: MutableList<Card> = ArrayList()

    init {
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                cards.add(Card(rank, suit))
            }
        }
        cards.shuffle()
    }

    fun drawCard(): Card? {
        return if (cards.isNotEmpty()) cards.removeAt(0) else null
    }
}

fun Player.chooseCardIndex(): Int {
    println("$name, выберите карту (введите индекс от 0 до ${hand.size - 1}): ")
    return readLine()?.toIntOrNull() ?: -1
}

fun main() {
    val game = Game()
    game.play()
}
