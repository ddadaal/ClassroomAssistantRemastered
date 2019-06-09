package nju.classroomassistant.student.util

import java.io.*

class HistoryStack: List<String> {
    override fun contains(element: String): Boolean {
        return data.contains(element)
    }

    override fun containsAll(elements: Collection<String>): Boolean {
        return data.containsAll(elements)
    }

    override fun get(index: Int): String {
        return data[index]
    }

    override fun indexOf(element: String): Int {
        return data.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun iterator(): Iterator<String> {
        return data.iterator()
    }

    override fun lastIndexOf(element: String): Int {
        return data.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<String> {
        return data.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<String> {
        return data.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<String> {
        return data.subList(fromIndex, toIndex)
    }

    private val data = ArrayList<String>()
    private val MAX_COUNT = 5

    fun append(item: String) {
        if (data.contains(item)) {
            data.remove(item)
        }
        data.add(item)

        if (size > MAX_COUNT) {
            data.removeAt(0)
        }
    }

    val latest get() = if (size == 0) null else data[size-1]

    override val size get() = data.size

    fun serialize(): String {
        return data.joinToString()
    }

    companion object {
        fun fromSerialized(serialized: String): HistoryStack {
            val stack = HistoryStack()
            serialized.split(",").forEach { stack.append(it) }
            return stack
        }
    }




}