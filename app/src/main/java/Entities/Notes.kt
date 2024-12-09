class Notes {

    private var _id: String = ""
    private var _noteName: String = ""
    private var _description: String = ""
    private var _image: ByteArray? = null

    constructor(id: String, noteName: String, description: String, image: ByteArray?) {
        this._id = id
        this._noteName = noteName
        this._description = description
        this._image = image
    }

    // Getters y setters
    var id: String
        get() = this._id
        set(value) { this._id = value }

    var noteName: String
        get() = this._noteName
        set(value) { this._noteName = value }

    var description: String
        get() = this._description
        set(value) { this._description = value }

    var image: ByteArray?
        get() = this._image
        set(value) { this._image = value }
}
