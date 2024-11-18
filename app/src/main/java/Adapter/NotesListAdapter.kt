package Adapter

import Entities.Notes
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.blopix.synclist.R
import java.io.File

class NotesAdapter(
    private val context: Context,
    private var resource: Int,
    private var datasource: List<Notes>
) : ArrayAdapter<Notes>(context, resource, datasource) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(resource, parent, false)

        // Asignar la vista a las variables
        val txtNoteId = rowView.findViewById<TextView>(R.id.note_id)
        val txtDescription = rowView.findViewById<TextView>(R.id.txt_fullDescription)
        val imgIcon = rowView.findViewById<ImageView>(R.id.imgitem_foto)

        // Obtener el objeto Note correspondiente a esta posición
        val note = datasource[position]

        // Asignar datos a las vistas
        txtNoteId.text = note.id
        txtDescription.text = note.description

        // Aquí puedes cargar la imagen de la nota, como en tu código anterior
        val imgFile = File(note.icon)
        if (imgFile.exists()) {
            imgIcon.setImageURI(Uri.fromFile(imgFile))
        } else {
            imgIcon.setImageResource(R.drawable.foto_item_background) // Imagen por defecto
        }

        return rowView
    }
}
