import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CardTest {
    @Test
    fun cardToStringTest() {
        val card = Card(Rank.ACE, Suit.HEARTS)
        assertEquals("Туз Черви", card.toString())
    }
}

class PlayerTest {
    private lateinit var player: Player

    @Before
    fun setup() {
        player = Player("Игрок 1")
    }

    @Test
    fun takeCardTest() {
        val card = Card(Rank.SIX, Suit.DIAMONDS)
        player.takeCard(card)
        assertTrue(player.hand.contains(card))
    }

    @Test
    fun playCardTest() {
        val card = Card(Rank.SEVEN, Suit.CLUBS)
        player.takeCard(card)
        val playedCard = player.playCard(0)
        assertEquals(card, playedCard)
    }
}

class DeckTest {
    private lateinit var deck: Deck

    @Before
    fun setup() {
        deck = Deck()
    }

    @Test
    fun drawCardTest() {
        val card = deck.drawCard()
        assertTrue(card != null)
    }
}
