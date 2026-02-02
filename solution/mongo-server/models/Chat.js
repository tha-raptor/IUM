const mongoose = require('mongoose');

const ChatSchema = new mongoose.Schema({
    movieId: { type: String, required: true },
    user: { type: String, required: true },
    text: { type: String, required: true },
    timestamp: { type: String, required: true }
}, { collection: 'chats' });

module.exports = mongoose.model('Chat', ChatSchema);