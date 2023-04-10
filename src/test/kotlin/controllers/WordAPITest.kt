package controllers

import models.Word
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Utils
import kotlin.test.*


class WordAPITest {

    private var word1: Word? = null
    private var word2: Word? = null
    private var word3: Word? = null
    private var word4: Word? = null
    private var word5: Word? = null

    private var emptyWords: WordAPI? = WordAPI()
    private var populatedWords: WordAPI? = WordAPI()

    @BeforeEach
    fun setUp() {

        word1 = Word("Happy", "A good feeling", 1, "feeling or showing pleasure or contentment")
        word2 = Word(
            "Friend",
            "Social relationship",
            1,
            "a person whom one knows and with whom one has a bond of mutual affection, typically exclusive of sexual or family relations"
        )
        word3 = Word("Delicious", "Taste", 3, "highly pleasant to the taste")
        word4 = Word(
            "Blue",
            "Color",
            4,

            "a color intermediate between green and violet, as of the sky or sea on a sunny day"
        )
        word5 = Word("Funny", "Humor", 5, "causing laughter or amusement; humorous")

        populatedWords!!.addWord(word1!!)
        populatedWords!!.addWord(word2!!)
        populatedWords!!.addWord(word3!!)
        populatedWords!!.addWord(word4!!)
        populatedWords!!.addWord(word5!!)


    }

    @AfterEach
    fun tearDown() {
        word1 = null
        word2 = null
        word3 = null
        word4 = null
        word5 = null

        emptyWords = null
        populatedWords = null
    }

    @Nested
    inner class AddingRemovingWords {

        @Test
        fun `adding a word adds that word`() {
            assertEquals(5, populatedWords!!.numberOfWords())
            assertTrue(populatedWords!!.addWord(Word("test", "test", 1, "test")))
            assertEquals(6, populatedWords!!.numberOfWords())

        }

        @Test
        fun `adding a duplicate word does not add that word and returns false`() {
            assertEquals(5, populatedWords!!.numberOfWords())
            assertEquals(populatedWords!!.getWordByIndex(0)!!.word.lowercase(), "happy")
            assertFalse(populatedWords!!.addWord(Word("HaPpY", "test", 1, "test")))
            assertEquals(5, populatedWords!!.numberOfWords())
        }

        @Test
        fun `removing a word removes that word`() {
            assertEquals(5, populatedWords!!.numberOfWords())
            assertEquals(word2, populatedWords!!.removeWordByIndex(1))
            assertEquals(4, populatedWords!!.numberOfWords())
            assertFalse(Utils.checkIsObjectPresent(word2!!, populatedWords!!.getWords()))
        }

        @Test
        fun `number of words returns correct number`() {
            assertEquals(0, emptyWords!!.numberOfWords())
            assertTrue(emptyWords!!.addWord(word1!!))
            assertTrue(Utils.checkIsObjectPresent(word1!!, emptyWords!!.getWords()))
            assertEquals(1, emptyWords!!.numberOfWords())
        }

    }

    @Nested
    inner class ListingWords {

        @Test
        fun `show all words shows all words`() {
            assertTrue(populatedWords!!.showAllWords().contains("Happy"))
            assertTrue(populatedWords!!.showAllWords().contains("1"))
            assertTrue(populatedWords!!.showAllWords().contains("Funny"))
            assertTrue(populatedWords!!.showAllWords().contains("5"))
        }

        @Test
        fun `showing all words when there is no words returns No saved words`() {
            assertEquals(0, emptyWords!!.numberOfWords())
            assertTrue(emptyWords!!.showAllWords().contains("No saved words"))
        }

        @Test
        fun `list all solved words lists all solved words`() {
            word1!!.solved = true
            word2!!.solved = true
            assertTrue(populatedWords!!.listAllSolvedWords().contains(word1!!.word))
            assertTrue(populatedWords!!.listAllSolvedWords().contains(word2!!.word))
        }

        @Test
        fun `list all solved words returns no words have been solved string if no words were solved`() {
            assertTrue(populatedWords!!.listAllSolvedWords().contains("No words have been solved!"))
        }

        @Nested
        inner class GettingWords {
            @Test
            fun `getting a random word returns a word`() {
                assertEquals(word3!!, populatedWords!!.getRandomWord(3))
            }

            @Test
            fun `getting a random word by difficulty only returns words of that difficulty`() {
                val word = populatedWords!!.getRandomWord(4)
                assertEquals(4, word!!.difficulty)
            }

            @Test
            fun `getting a random word will still return a word if that difficulty is not present`(){
                val word = populatedWords!!.getRandomWord(2)
                assertNotNull(word)
                assertFalse(word.difficulty==2)
            }
        }

    }
}