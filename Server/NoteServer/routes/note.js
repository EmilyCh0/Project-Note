const express = require('express');
const router = express.Router();
const Note = require('../models/note');

// insert one note
router.post('/', (req, res, next) => {
    const note = new Note({
        title: req.body.title,
        content: req.body.content,
        timestamp: req.body.timestamp
    })
    note.save()
        .then((result) => {
            res.json(result);
        })
        .catch((error) => {
            console.error(error);
            next(error);
        })
})

// get all 
router.get('/list', (req, res, next) => {
    let page = req.query.page;

    var query = Note.find()
        .sort('timestamp')
        .skip(page > 0?(page - 1)*5:0)
        .limit(5)
        
    query.exec()
        .then((notes) => {
            res.json(notes);
        })
        .catch((error) => {
            console.error(error);
            next(error);
        })
});




module.exports = router