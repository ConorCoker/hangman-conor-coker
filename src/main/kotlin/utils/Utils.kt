package utils

object Utils {

    @JvmStatic
    fun isValidIndex(index: Int, list: List<Any>): Boolean {
        return index >= 0 && index < list.size
    }

    @JvmStatic
    fun checkIsObjectPresent(obj:Any,list: List<Any>) = list.contains(obj)
}