package space.tuleuov.bookreader.books.model

import space.tuleuov.bookreader.R

class TestData {

    fun loadLocalBooks(): List<LocalBook> {
        return listOf(
            LocalBook(
                id = 1,
                title = "Book 1",
                author = "Author 1",
                genre = "Genre 1",
                pageCount = 200,
                yearOfPublication = 2020,
                coverResId = R.drawable.one_piece
            ),
            LocalBook(
                id = 2,
                title = "Book 2",
                author = "Author 2",
                genre = "Genre 2",
                pageCount = 250,
                yearOfPublication = 2019,
                coverResId = R.drawable.clashofkings
            ),
            LocalBook(
                id = 3,
                title = "Book 3",
                author = "Author 3",
                genre = "Genre 3",
                pageCount = 300,
                yearOfPublication = 2021,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 4,
                title = "Book 4",
                author = "Author 4",
                genre = "Genre 4",
                pageCount = 220,
                yearOfPublication = 2018,
                coverResId = R.drawable.harry_potter
            ),
            LocalBook(
                id = 5,
                title = "Book 5",
                author = "Author 5",
                genre = "Genre 5",
                pageCount = 280,
                yearOfPublication = 2022,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 6,
                title = "Book 6",
                author = "Author 6",
                genre = "Genre 6",
                pageCount = 240,
                yearOfPublication = 2017,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 7,
                title = "Book 7",
                author = "Author 7",
                genre = "Genre 7",
                pageCount = 190,
                yearOfPublication = 2015,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 8,
                title = "Book 8",
                author = "Author 8",
                genre = "Genre 8",
                pageCount = 270,
                yearOfPublication = 2023,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 9,
                title = "Book 9",
                author = "Author 9",
                genre = "Genre 9",
                pageCount = 310,
                yearOfPublication = 2016,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 10,
                title = "Book 10",
                author = "Author 10",
                genre = "Genre 10",
                pageCount = 230,
                yearOfPublication = 2014,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 11,
                title = "Book 11",
                author = "Author 11",
                genre = "Genre 11",
                pageCount = 260,
                yearOfPublication = 2024,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 12,
                title = "Book 12",
                author = "Author 12",
                genre = "Genre 12",
                pageCount = 280,
                yearOfPublication = 2022,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 13,
                title = "Book 13",
                author = "Author 13",
                genre = "Genre 13",
                pageCount = 190,
                yearOfPublication = 2015,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 14,
                title = "Book 14",
                author = "Author 14",
                genre = "Genre 14",
                pageCount = 270,
                yearOfPublication = 2023,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 15,
                title = "Book 15",
                author = "Author 15",
                genre = "Genre 15",
                pageCount = 310,
                yearOfPublication = 2016,
                coverResId = R.drawable.i565152
            )
        )
    }
}