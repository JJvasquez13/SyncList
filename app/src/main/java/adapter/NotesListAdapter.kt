package adapter

import Notes
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.blopix.synclist.R

class NotesListAdapter(
    private val context: Context,
    private var resource: Int,
    private var datasource: List<Notes> // Cambiar de Notes? a List<Notes>
) : ArrayAdapter<Notes>(context, resource, datasource) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size // Devuelve el tamaño de la lista
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.note_item_list, parent, false)
        val lbFullName = rowView.findViewById(R.id.txt_noteName) as TextView
        val lbDescription = rowView.findViewById(R.id.txt_fullDescription) as TextView
        val imgPhoto = rowView.findViewById(R.id.imgitem_foto) as ImageView

        val note = datasource[position]

        // Mostrar el nombre de la nota
        lbFullName.text = note.noteName

        // Mostrar la descripción de la nota
        lbDescription.text = note.description

        // Intentar obtener la imagen como un ByteArray
        val imageByteArray: ByteArray? = note.image

        // Cargar la imagen de la nota desde el ByteArray almacenado en la base de datos
        if (imageByteArray != null) {
            val bitmap = convertByteArrayToBitmap(imageByteArray)
            imgPhoto.setImageBitmap(bitmap)
        } else {
            // Si no hay imagen, usa una imagen predeterminada
            imgPhoto.setImageResource(R.drawable.foto_item_background) // Imagen predeterminada
        }

        return rowView
    }

    private fun convertByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
