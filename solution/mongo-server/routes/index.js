const express = require('express');
const createError = require('http-errors');
const router = express.Router();
const Review = require('../models/Review');
const Chat = require('../models/Chat');

const port = 3001;


router.get('/', (req, res) => {
    res.send('Reviews API running on port: ' + port + '');
});

router.get('/api/reviews/search', async (req, res, next) => {
    const title = req.query.movie_title;

    if (!title) {
        next(createError(500, 'Database query failed'));
    }

    try {
        const regEx = new RegExp(["^", title, "$"].join(""), "i");
        const reviews = await Review.find({ 'movie_title': regEx })
            .limit(10)
            .sort({ review_date: -1 });
        console.log("searched for: ", title, "----> Found:", reviews.length);

        res.json(reviews);
    } catch (error) {
        next(createError(500, 'Database query failed'));
    }
});


router.post('/api/chat/save', async (req, res)=>{
   try {
       const { movieId, user, text, timestamp } = req.body;
       const newChat = new Chat({ movieId, user, text, timestamp });
       await newChat.save();
       console.log(user + " chat saved at" + timestamp);
       res.status(200).json({ status: 'Chat Saved' });
   } catch (error) {
       res.status(400).json({ error: error.message });
   }
});

router.get('/api/chat/history', async (req, res, next) => {
    const movieId = req.query.movieId;
    console.log("movieId"+movieId);
    if (!movieId) {
        next(createError(500, 'Database query failed'));
    }
    try {
        const messages = await Chat.find({ 'movieId': movieId });
        console.log("searched for: ", movieId, "----> Found:", messages.length);

        res.json(messages);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

/*router.post('/api/reviews/search', async (req, res) => {
    try {
        const newReview = new Review(req.body);
        await newReview.save();
        res.status(201).json(newReview);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});*/

module.exports = router;