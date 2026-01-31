const axios = require('axios');
const MONGO_SERVER_URL = 'http://localhost:3001/api';

exports.init = function(io) {
    io.on('connection', function (socket) {
        console.log("New client connected: " + socket.id);
        try {
            socket.on('join_room',function(movieId){
                const room = String(movieId);
                socket.join(room);
                console.log(`User joined room of film_id: ${movieId}`);
            });

            socket.on('message', async function (movieId, user, text, time ){
                const room = String(movieId);
                io.to(room).emit('message', room, user, text, time);

                try {
                    await axios.post(`${MONGO_SERVER_URL}/chat/save`, {
                        movieId: movieId,
                        user: user,
                        text: text,
                        timestamp: time
                    });
                    console.log(`Message saved in room ${movieId}`);
                } catch (err) {
                    console.error("Error saving chat on Mongo:", err.message);
                }
            });

            socket.on('disconnect', function (){
                console.log('User Disconnected');
            })
        }
        catch (error) {
            console.log("error: " + error);
        }
    });
}