import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "notes.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NOTES = "notes"

        const val COLUMN_ID = "id"
        const val COLUMN_NOTE_NAME = "note_name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"  // Nueva columna para la imagen
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_NOTES_TABLE = """
            CREATE TABLE $TABLE_NOTES (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NOTE_NAME TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IMAGE BLOB
            );
        """
        db.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }
}
