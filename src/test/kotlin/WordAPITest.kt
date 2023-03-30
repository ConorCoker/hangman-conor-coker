import controllers.WordAPI
import models.Word
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


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

        word1 = Word("Happy","A good feeling",7,1,"feeling or showing pleasure or contentment")
        word2 = Word("Friend","Social relationship",8,2,"a person whom one knows and with whom one has a bond of mutual affection, typically exclusive of sexual or family relations")
        word3 = Word("Delicious", "Taste", 9, 3, "highly pleasant to the taste")
        word4 = Word("Blue", "Color", 6, 1, "a color intermediate between green and violet, as of the sky or sea on a sunny day")
        word5 = Word("Funny", "Humor", 7, 2, "causing laughter or amusement; humorous")

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
    inner class AddingWords{

        @Test
        fun `adding a word adds that word`(){
            assertEquals(5,populatedWords!!.numberOfWords())
            populatedWords!!.addWord(Word("test","test",1,1,"test"))
            assertEquals(6,populatedWords!!.numberOfWords())

        }

    }
}