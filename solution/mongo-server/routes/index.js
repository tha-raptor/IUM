const express = require('express');
const router = express.Router();
const Review = require('../models/Review'); // Reaching up to your models folder

const port = 3001;


router.get('/', (req, res) => {
    res.send('Reviews API running on port: ' + port + '');
});


router.get('/api/reviews/search', async (req, res) => {
    const title = req.query.title;

    if (!title) {
        return res.status(400).send('Title is required');
    }

    try {
        const regEx = new RegExp(["^", title, "$"].join(""), "i");
        const reviews = await Review.find({ 'movie_title': regEx });
        console.log("searched for: ", title, "----> Found:", reviews.length);

        res.json(reviews);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

router.post('/api/reviews/search', async (req, res) => {
    try {
        const newReview = new Review(req.body);
        await newReview.save();
        res.status(201).json(newReview);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
});

module.exports = router;