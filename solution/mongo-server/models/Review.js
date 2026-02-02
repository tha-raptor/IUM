const mongoose = require('mongoose');

const reviewSchema = new mongoose.Schema({
    movie_title: { type: String, required: true, index: true },
    rotten_tomatoes_link: String,
    critic_name: String,
    top_critic: Boolean,
    publisher_name: String,
    review_type: String,
    review_date: Date,
    review_content: String,
    score: Number
}, { collection: 'reviews' });

module.exports = mongoose.model('Review', reviewSchema);