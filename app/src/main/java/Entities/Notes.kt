package Entities

class Notes {
    private var _id: String = ""
    private var _description: String = ""
    private var _recordatory: Int = 0
    private var _shareWith: String = ""
    private var _icon: String = ""
    private var _share: Boolean = false

    constructor()

    constructor(
        id: String,
        description: String,
        recordatory: Int,
        shareWith: String,
        icon: String,
        share: Boolean
    ) {
        this._id = id
        this._description = description
        this._recordatory = recordatory
        this._shareWith = shareWith
        this._icon = icon
        this._share = share
    }

    var id: String
        get() = this._id
        set(value) {
            this._id = value
        }

    var description: String
        get() = this._description
        set(value) {
            this._description = value
        }

    var recordatory: Int
        get() = this._recordatory
        set(value) {
            this._recordatory = value
        }

    var shareWith: String
        get() = this._shareWith
        set(value) {
            this._shareWith = value
        }
    var icon: String
        get() = this._icon
        set(value) {
            this._icon = value
        }

    var share: Boolean
        get() = this._share
        set(value) {
            this._share = value
        }
}