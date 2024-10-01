import java.util.Scanner

fun main() {
    NoteBook().start()
}

class NoteBook {
    private val scanner = Scanner(System.`in`)
    private val archiveNotes: MutableList<ArchiveNotes> = mutableListOf()

    fun start() {
        showStartScreen()

    }

    private fun showStartScreen() {
        while (true) {
            val menu = createMenu()
            when (val selectedMenuItem = getMenuItem(menu)) {
                is MenuItem.Create -> {
                    val name = getStringFromUser("название архива")
                    val archive = ArchiveNotes(name, mutableListOf())
                    archiveNotes.add(archive)
                }

                is MenuItem.Item -> {
                    val archive = archiveNotes.first { it.name == selectedMenuItem.name }
                    showArchiveScreen(archive)
                }

                is MenuItem.Exit -> {
                    println("Работа завершена")
                    break
                }
            }
        }
    }

    private fun showArchiveScreen(archive: ArchiveNotes) {
        while (true) {
            val menu = createMenu(archive)
            when (val selectedMenuItem = getMenuItem(menu)) {
                is MenuItem.Create -> {
                    val title = getStringFromUser("имя заметки")
                    val message = getStringFromUser("текст заметки")
                    val note = Note(title, message)
                    archive.notesList.add(note)
                }

                is MenuItem.Item -> {
                    val note = archive.notesList.find { it.title == selectedMenuItem.name }
                    if (note != null) {
                        note.print()
                    } else {
                        println("Заметка не найдена")
                    }
                }

                is MenuItem.Exit -> {
                    break
                }
            }
        }
    }

    private fun getMenuItem(menu: Menu): MenuItem {
        while (true) {
            menu.print()
            val menuNumber = scanner.nextLine()
            try {
                val number = menuNumber.toInt()
                if (number !in 0..archiveNotes.size + 1) {
                    println("такого элемента нет на экране, введите корректный символ")
                } else {
                    return menu.menuItems[number]
                }
            } catch (e: Exception) {
                println("Следует вводить цифру")
            }
        }
    }

    private fun getStringFromUser(message: String): String {
        while (true) {
            println("Введите $message")
            val text = scanner.nextLine()
            if (text.isNotBlank()) {
                return text
            } else {
                println("$message не может быть пустым")
            }
        }
    }

    private fun createMenu(archive: ArchiveNotes? = null): Menu {
        return Menu(
            name = if (archive == null) {
                "Список архивов:"

            } else {
                "Список заметок в архиве: ${archive.name}"
            },
            menuItems = buildList {
                add(
                    MenuItem.Create(
                        if (archive == null) {
                            "Создать архив"
                        } else {
                            "Создать заметку"
                        }
                    )
                )

                addAll(
                    if (archive == null) {
                        archiveNotes.map { MenuItem.Item(name = it.name) }
                    } else {
                        archive.notesList.map { MenuItem.Item(name = it.title) }
                    }
                )

                add(MenuItem.Exit("Выход"))
            }
        )
    }
}
