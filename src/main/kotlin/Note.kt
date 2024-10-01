data class Note(
    val title: String,
    val message: String
) {
    fun print() {
        println("""
            *****************
            Название заметки: $title
            
            $message
            *****************            
        """.trimIndent())
    }
}