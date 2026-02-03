const express = require('express');
const router = express.Router();
const axios = require('axios');
const createError = require('http-errors');

const SPRING_BOOT_URL = 'http://localhost:8080/api/v1';
const MONGO_API_URL = 'http://localhost:3001/api';

router.get('/', (req, res, next) => {
    res.render('index', { title: 'MovieApp' });
});

router.get('/paged', async (req, res, next) => {
    const page = req.query.page || 0;
    try {
        const response = await axios.get(`${SPRING_BOOT_URL}/movies/paged`,{
            params: {
                page: page,
                size: 30
            }
        });
        res.json(response.data);
    } catch (error) {
        handleError(error, next)
    }
});

router.get('/getMovie/:id', async (req, res, next) => {
   const movieId = req.params.id;

   try {
       const response = await axios.get(`${SPRING_BOOT_URL}/movies/${movieId}`);
       const movieData = response.data;
       res.json(movieData);
   }catch (error) {
       handleError(error, next);
   }
});

router.get('/genre/:name', async (req, res, next) => {

    const page = req.query.page || 0;
    const genreName = req.params.name;

    if (!genreName) {
        handleError(new Error('Parameter \'Genre\' is required'), next);
    }
    try {
        console.log(`Forwarding genre search for: ${genreName} to Spring Boot`);
        const response = await axios.get(`${SPRING_BOOT_URL}/movies/genre/${genreName}`,{
                    params: {
                        page: page,
                        size: 30
                    }
                });
        res.json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

router.get('/search', async (req, res, next) => {
    const title = req.query.q;

    if (!title) {
        handleError(new Error('Parameter \'title\' is required'), next);
    }
    try {
        const response = await axios.get(`${SPRING_BOOT_URL}/movies/search`, {
            params: { name: title }
        });

        res.json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

router.get('/searchReviews', async (req, res, next) => {
    const title = req.query.title;

    if (!title) {
        handleError(new Error('Parameter \'title\' is required'), next);
    }

    try {
        const response = await axios.get(`${MONGO_API_URL}/reviews/search`, {
            params: { movie_title: title }
        });

        res.json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

router.post('/saveChat', async (req, res, next)=>{

    try {
        const response = await axios.post(`${MONGO_API_URL}/chat/save`,{
            movieId,
            user,
            text,
            timestamp
        });

        res.status(201).json(response.data);
    } catch (error) {
        handleError(error, next);
    }
})

router.get('/loadChat', async (req, res, next)=>{
    const movieId = req.query.movieId;
    if (!movieId) {
       return next(createError(400, 'Parameter \'movieId\' is required'));
    }
    try {
        console.log(movieId)
        const response = await axios.get(`${MONGO_API_URL}/chat/history`, {
            params: { movieId: movieId }
        });

        res.status(201).json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

router.post('/addReview', async (req, res, next) => {
    try {
        const response = await axios.post(`${MONGO_API_URL}/reviews/search`, req.body);

        res.status(201).json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

function handleError(error, next) {
    console.log(error.message);
    if (error.response) {
        next(createError(error.response.status, `Microservice Error: ${error.message}`));
    } else {
        next(createError(500, error.message));
    }
}

module.exports = router;