//package space.tuleuov.bookreader
//
//import android.content.Context
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import space.tuleuov.bookreader.db.Database
//import space.tuleuov.bookreader.hyphe.Haaivin
//import space.tuleuov.bookreader.hyphe.HunspellDictionary
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext context: Context): Database {
//        return Room.databaseBuilder(
//            context,
//            Database::class.java,
//            "bookReader"
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideHyphRu(@ApplicationContext context: Context): Haaivin {
//        return Haaivin(listOf(HunspellDictionary("ruhyph") { context.assets.open("hyph_ru_RU.dic") }))
//    }
//}
