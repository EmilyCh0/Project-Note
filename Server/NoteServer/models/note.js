const mongoose = require('mongoose')

var Schema = mongoose.Schema;

const noteSchema = new Schema({
    id:{
        type: String
    },
    timestamp:{
        type: Date,
        default: Date.now
    },
    title: {
        type: String,
    },
    content: {
        type: String
    },
    fav: {
        type: Boolean,
        default: false
    }

});

module.exports = mongoose.model('Note', noteSchema);