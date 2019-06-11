package nju.classroomassistant.teacher.repository

import java.io.*


abstract class Repository<T : Serializable>(
    name: String,
    val defaultValue: T
) {

    var filePath = getFilePath(name)

    open var data: T = retrieveData()

    @Suppress("UNCHECKED_CAST")
    private fun retrieveData(): T {
        synchronized(Repository::class.java) {
            FileInputStream(filePath).use { fileIn ->
                return try {
                    ObjectInputStream(fileIn).use { objIn ->
                        val obj = objIn.readObject() as T
                        obj
                    }
                } catch (e: Exception) {
                    save(defaultValue)
                    defaultValue
                }
            }
        }
    }

    fun save(data: T = this.data) {
        synchronized(Repository::class.java) {
            FileOutputStream(filePath).use { fileOut ->
                ObjectOutputStream(fileOut).use { out ->
                    out.writeObject(data)
                    this.data = data
                }
            }
        }

    }

    private fun getFilePath(name: String): String {
        val path = Repository::class.java.protectionDomain.codeSource.location.path
        val paths = path.split("!".toRegex()).dropLastWhile { it.isEmpty() }
        val jarFile = File(paths[0])

        val parentDir = jarFile.parent

        // ensure data folder exists
        val dataFolder = File("$parentDir/data")
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }

        // ensure data file exists
        val dataFile = File("$parentDir/data/$name")
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }

        return dataFile.absolutePath

    }

}