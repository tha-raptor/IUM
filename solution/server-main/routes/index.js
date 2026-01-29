const express = require('express');
const router = express.Router();
const axios = require('axios');
const createError = require('http-errors');

const SPRING_BOOT_URL = 'http://localhost:8080/api/v1';
const MONGO_API_URL = 'http://localhost:3001/api/reviews/search';

router.get('/', (req, res, next) => {
    res.render('index', { title: 'DB Movie' });
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

router.get('/search', async (req, res, next) => {
    const title = req.query.q;

    if (!title) {
        handleError(new Error('Parameter \'q\' is required'), next);
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
        return next(createError(400, 'Parameter \'title\' is required'));
    }

    try {
        const response = await axios.get(MONGO_API_URL, {
            params: { movie_title: title }
        });

        res.json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

router.post('/addReview', async (req, res, next) => {
    try {
        const response = await axios.post(MONGO_API_URL, req.body);

        res.status(201).json(response.data);
    } catch (error) {
        handleError(error, next);
    }
});

function handleError(error, next) {
    if (error.response) {
        next(createError(error.response.status, `Microservice Error: ${error.message}`));
    } else {
        next(createError(500, error.message));
    }
}

module.exports = router;