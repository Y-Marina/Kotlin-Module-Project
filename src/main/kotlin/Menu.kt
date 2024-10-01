data class Menu(
    val name: String,
    val menuItems: List<MenuItem>
) {
    fun print() {
        println("******************")
        println(name)
        menuItems.forEachIndexed { index, menuItem -> println("${index}. ${menuItem.name}") }
        println("******************")
    }
}



sealed class MenuItem {
    abstract val name:String
    data class Create(override val name: String) : MenuItem()
    data class Item(override val name: String) : MenuItem()
    data class Exit(override val name: String) : MenuItem()
}