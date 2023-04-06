package utils

object Utils {

    @JvmStatic
    fun isValidIndex(index: Int, list: List<Any>): Boolean {
        return index >= 0 && index < list.size
    }

    @JvmStatic
    fun checkIsObjectPresent(obj: Any, list: List<Any>) = list.contains(obj)

    @JvmStatic
    fun charListContainsIgnoreCase(list: List<Any>,char:Char ) = list.contains(char) || list.contains(char.lowercase())
    // underscores.indexOf(guess)
    @JvmStatic
    fun indexOfCharIgnoreCase(list: List<Any>, char:Char):Int{
        list.forEach {
            if (it == char || it == char.lowercase()) return list.indexOf(it)
        }
        return -1
    }

}