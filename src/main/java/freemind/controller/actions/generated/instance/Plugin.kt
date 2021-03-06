package freemind.controller.actions.generated.instance

import java.util.Collections

/* Plugin...*/ class Plugin {
    /* constants from enums*/
    var label: String? = null
    fun addChoice(choice: Any?) {
        choiceList.add(choice)
    }

    fun addAtChoice(position: Int, choice: Any?) {
        choiceList.add(position, choice)
    }

    fun setAtChoice(position: Int, choice: Any?) {
        choiceList.set(position, choice)
    }

    fun getChoice(index: Int): Any {
        return choiceList[index] as Any
    }

    fun sizeChoiceList(): Int {
        return choiceList.size
    }

    fun clearChoiceList() {
        choiceList.clear()
    }

    val listChoiceList: List<*>
        get() = Collections.unmodifiableList(choiceList)
    protected var choiceList: ArrayList<Any?> = ArrayList<Any?>()
} /* Plugin*/
