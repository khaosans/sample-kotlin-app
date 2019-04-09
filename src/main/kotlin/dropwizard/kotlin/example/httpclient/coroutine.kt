fun isDuplicate(word: String) {


    val listOf = listOf(1, 2, 3)


    val reduce: Int = listOf.reduce { acc, element -> acc + element }

    print(reduce)

}

enum class Type {
    SCIFI, FICTION, MATH, COMEDY, FANTASY, CARTOON
}

data class Movie(val name: String, val type: Type, val price: Int, val inStock: Boolean)

fun main(args: Array<String>) {
    val books = listOf(
            Movie("Beauty and the beast", Type.CARTOON, 2, true),
            Movie("A.I.", Type.SCIFI, 5, false),
            Movie("Star Trek", Type.SCIFI, 12, true),
            Movie("Harry Potter", Type.FANTASY, 8, false),
            Movie("Rush Hour", Type.COMEDY, 10, false))


    val totalPrice = books.filter { it.type == Type.SCIFI && it.inStock }.map { it.price }.sum()


    val item = books.distinctBy { it.type }.toList()

    val first: Movie = books.first { it.inStock }


    val dropLastWhile = books.dropLastWhile {  book -> !book.inStock }

    val lessSciFi = books.dropLastWhile { b -> b.type == Type.SCIFI }


}