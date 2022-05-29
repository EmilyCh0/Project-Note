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

// get fav
router.get('/list/fav', (req, res, next) => {
    let page = req.query.page;

    var query = Note.find({'fav': true})
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
})

// search with options (start, end)
router.get('/list/options', (req, res, next) => {
    let page = req.query.page;
    let start = new Date(req.query.start);
    let end = new Date(req.query.end);
    end.setDate(end.getDate() + 1);
    //let key = req.query.key;
    var query = Note.find(
        {'timestamp': {$gte: start, $lt: end}},
        //{$or: [{ 'title': {$regex: key}}, {'content': {$regex: key}}]}
    ).sort('timestamp')
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
})

// findByIdAndUpdate
router.patch('/:id', (req, res, next) => {
    
    Note.findByIdAndUpdate(
        req.params.id,
        {
            $set:
            {
                timestamp: req.body.timestamp,
                title: req.body.title,
                content: req.body.content,
                fav: req.body.fav
            }
        },
        function(err, note){
            if(err){
                console.log(err)
            }else{
                res.json(note)
            }
        }
        )

})

router.delete('/:id', (req, res, next) =>{
    Note.findByIdAndDelete(
        req.params.id,
        function(err, note){
            if(err){
                console.log(err)
            }else{
                res.json(note)
            }
        }
    )
    
})



module.exports = router