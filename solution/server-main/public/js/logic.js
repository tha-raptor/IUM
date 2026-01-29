let currentPage = 0;
let isLoading = false;
let hasMoreMovies = true;
const PLACEHOLDER_IMG = 'https://placehold.co/300x450?text=No+Poster';

document.addEventListener('DOMContentLoaded', () => {
    console.log("Static js Initialized");

    const searchForm = document.getElementById('search-form');

    loadMoreMovies();

    window.addEventListener('scroll', () => {
        const gridExists = document.getElementById('movie-grid');
        if((window.innerHeight + window.scrollY >= document.body.offsetHeight - 100)&& gridExists) {
            loadMoreMovies();
        }
    })

    if (searchForm) {
        searchForm.addEventListener('submit', (e) => executeSearch(e));
    }
});

async function executeSearch(e) {
    e.preventDefault();
    console.log("executeSearch()");
    const query = document.getElementById('search-input').value;
    const contentDiv = document.getElementById('main-content');

    if (!query) return;

    showSpinner(true);

    try {
        const response = await fetch(`/search?q=${encodeURIComponent(query)}`);

        if (!response.ok) throw new Error("Errore nella ricerca");

        const movies = await response.json();
        console.log(movies);
        if (movies.length === 0) {
            contentDiv.innerHTML = '<div class="alert alert-warning">Nessun film corrisponde ai criteri di ricerca.</div>';
            return;
        }
        contentDiv.innerHTML = `
            <h3 class="mb-4">Risultati ricerca</h3>
            <div id="movie-grid" class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
                </div>
            <div id="scroll-spinner" class="text-center my-5 d-none">
                <div class="spinner-border text-primary" role="status"></div>
            </div>
        `;
        currentPage = 0;
        showSpinner(false);

        hasMoreMovies = false;
        appendMoviesToGrid(movies);

    } catch (error) {
        console.error(error);
        contentDiv.innerHTML = `<div class="alert alert-danger">Errore: ${error.message}</div>`;
    }
}

async function loadMoreMovies() {
    console.log("loadMoreMovies() called");

    if (isLoading || !hasMoreMovies) return;

    isLoading = true;
    showSpinner(true);

    try {
        console.log(`Caricamento pagina ${currentPage}...`);

        const response = await fetch(`/paged?page=${currentPage}`);
        const movies = await response.json();
        console.log(`Pagina ${currentPage} caricata.`);
        console.log(movies);
        if (movies.length === 0) {
            hasMoreMovies = false;
            showSpinner(false);
            return;
        }

        appendMoviesToGrid(movies);
        currentPage++;

    } catch (error) {
        console.error("Errore scroll:", error);
    } finally {
        isLoading = false;
        showSpinner(false);
    }
}

function appendMoviesToGrid(movies) {
    console.log("appendMoviesToGrid() called");

    const contentDiv = document.getElementById('main-content');

    if (currentPage === 0) {
        contentDiv.innerHTML = `
            <h3 class="mb-4">Top Rated Movies</h3>
            <div id="movie-grid" class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
                </div>
            <div id="scroll-spinner" class="text-center my-5 d-none">
                <div class="spinner-border text-primary" role="status"></div>
            </div>
        `;
    }

    const gridDiv = document.getElementById('movie-grid');

    const moviesHTML = movies.map(movie => `
        <div class="col animate-fade-in">
            <div class="card h-100 shadow-sm movie-card">
                <img src="${movie.link === 'no_link' ? PLACEHOLDER_IMG : movie.link}" 
                     class="card-img-top" 
                     alt="${movie.name}" 
                     style="height: 300px; object-fit: cover;">
                
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${movie.name}</h5>
                    ${movie.rating ? `<p class="card-text text-warning">★ ${movie.rating}</p>` : ''}
                    <button class="btn btn-primary mt-auto" onclick="loadMovieDetails('${movie.id}')">
                        Dettagli
                    </button>
                </div>
            </div>
        </div>
    `).join('');

    gridDiv.insertAdjacentHTML('beforeend', moviesHTML);
}

function showSpinner(show) {
    const spinner = document.getElementById('scroll-spinner');
    if (spinner) {
        if (show) spinner.classList.remove('d-none');
        else spinner.classList.add('d-none');
    }
}



async function loadMovieDetails(id) {
    console.log("loadMovieDetails() called");
    const contentDiv = document.getElementById('main-content');

    contentDiv.innerHTML = '<div class="text-center mt-5"><div class="spinner-border"></div><p>Loading data of Film...</p></div>';

    try {
        const movieResponse = await fetch(`/getMovie/${id}`);
        if (!movieResponse.ok) throw new Error("Errore nella ricerca dei film");

        const movie = await movieResponse.json();
        console.log(movie);

        let reviews = [];
        try {
            if (movie.name) {
                const reviewsResponse = await fetch(`/searchReviews?title=${encodeURIComponent(movie.name)}`)
                if (reviewsResponse.ok) {
                    reviews = await reviewsResponse.json();
                    console.log(reviews);
                }
            }
        } catch (reviewError) {
            console.warn("Errore nella ricerca delle reviews:", reviewError);
        }

        contentDiv.innerHTML = `
            <div class="animate-fade-in">
                <button class="btn btn-outline-secondary btn-sm mb-4 d-flex justify-content-start" onclick="location.reload()">← Back</button>
                
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="display-5">${movie.name} <small class="text-muted">(${movie.date})</small></h1>
                        <p class="lead italic">"${movie.tagline}"</p>
                        <hr>
                        <h5>Synopsis</h5>
                        <p>${movie.description}</p>
                        
                        <div class="mt-4">
                            <span class="badge bg-info text-dark">Durata: ${movie.minute} min</span>  
                            <span class="badge bg-warning text-dark">Rating: ${movie.rating}</span>
                        </div>
                    </div>
                    <div class="col-md-4 text-center">
                        <img src="${movie.poster?.link || PLACEHOLDER_IMG}"
                             class="img-fluid rounded shadow" 
                             alt="${movie.name}"
                             style="max-height: 690px; max-width: 460px; width: 100%; object-fit: cover;">
                    </div>
                </div>

                <div class="row mt-5">
                    <div class="col-12">
                        <h4>Reviews (da Rotten Tomatoes)</h4>
                        <div class="list-group mt-3">
                            ${reviews.length > 0 ? reviews.map(r => `
                                <div class="list-group-item border-start border-4 ${r.review_type === 'Fresh' ? 'border-success' : 'border-danger'}">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h6 class="mb-1">${r.critic_name} <small class="text-muted">from ${r.publisher_name}</small></h6>
                                        <span class="badge ${r.review_type === 'Fresh' ? 'bg-success' : 'bg-danger'}">${r.review_type}</span>
                                    </div>
                                    <p class="mb-1 small">"${r.review_content}"</p>
                                </div>
                            `).join('') : '<p class="text-muted">No reviews found for this title.</p>'}
                        </div>
                    </div>
                </div>
            </div>
        `;
    } catch (error) {
        console.error(error);
        contentDiv.innerHTML = `<div class="alert alert-danger">Failed to load movie details.</div>`;
    }
}
