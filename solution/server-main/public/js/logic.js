let socket;
let currentMovieId = null;
let currentPage = 0;
let isLoading = false;
let hasMoreMovies = true;
const PLACEHOLDER_IMG = 'https://placehold.co/300x450?text=No+Poster';

document.addEventListener('DOMContentLoaded', () => {
    console.log("Static js Initialized");

    const searchForm = document.getElementById('search-form');

    loadMoreMovies();

    window.addEventListener('scroll', () => {
        let gridExists = document.getElementById('movie-grid');
        let btnExists = document.getElementById('btn_back');
        if((window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) && gridExists && !isLoading && !btnExists) {
            loadMoreMovies();
        }
    })

    if(searchForm) {
        searchForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const query = document.getElementById('search-input').value;

            const spinner = document.getElementById('loading-spinner');
            if (spinner) spinner.classList.remove('d-none');

            document.getElementById('movie-grid').innerHTML = '';

            if (typeof executeSearch === "function") {
                console.log("searching for "+query);
                await executeSearch(query);
            } else {
                console.error("executeSearch function is missing!");
            }
        });
    }
});

async function executeSearch(query) {
    console.log("executeSearch()");
    const contentDiv = document.getElementById('main-content');

    if (!query) return;

    showSpinner(true);

    try {
        const response = await fetch(`/search?q=${encodeURIComponent(query)}`);

        if (!response.ok) throw new Error("Errore nella ricerca");

        const movies = await response.json();
        document.getElementById('loading-spinner').classList.add('d-none');

        if (movies.length === 0) {
            contentDiv.innerHTML = '<div class="alert alert-warning">Nessun film corrisponde ai criteri di ricerca.</div>';
            return;
        }
        renderMovies(movies, false);
        currentPage = 0;
        showSpinner(false);
        hasMoreMovies = false;
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
        renderMovies(movies, true);
        currentPage++;

    } catch (error) {
        console.error("Errore scroll:", error);
    } finally {
        isLoading = false;
        showSpinner(false);
    }
}

function showSpinner(show) {
    const spinner = document.getElementById('loading-spinner');
    if (spinner) {
        if (show) spinner.classList.remove('d-none');
        else spinner.classList.add('d-none');
    }
}

function toggleCast(btn) {
    const hiddenCast = document.getElementById('extra-cast');
    if (hiddenCast) {
        hiddenCast.classList.toggle('d-none');
        if (hiddenCast.classList.contains('d-none')) {
            btn.textContent = "Show All Cast";
        } else {
            hiddenCast.style.display = 'contents';
            btn.textContent = "Show Less";
        }
    }
}

function toggleCrew(btn) {
    const hiddenCast = document.getElementById('extra-crew');
    if (hiddenCast) {
        hiddenCast.classList.toggle('d-none');
        if (hiddenCast.classList.contains('d-none')) {
            btn.textContent = "▼";
        } else {
            btn.textContent = "▲";
        }
    }
}

function toggleChat() {
    const chatWindow = document.getElementById('chat-window');
    const fab = document.getElementById('chat-fab');

    if (chatWindow.classList.contains('d-none')) {
        chatWindow.classList.remove('d-none');
        chatWindow.classList.add('d-flex');

        fab.style.transform = "rotate(45deg)";
        fab.innerHTML = "✕";
    } else {
        chatWindow.classList.add('d-none');
        chatWindow.classList.remove('d-flex');

        fab.style.transform = "rotate(0deg)";
        fab.innerHTML = "💬";
    }
}

function renderMovies(movieList, shouldAppend = false) {
    const grid = document.getElementById('movie-grid');
    if (!shouldAppend) {
        grid.innerHTML = '';
    }

    movieList.forEach(movie => {
        const col = document.createElement('div');
        col.className = 'col animate-fade-in';

        col.innerHTML = `
            <div class="card movie-card h-100 text-white" style="background-color: #1f1f1f; border: none; cursor: pointer;">
                <img src="${movie.poster === 'no_link' ? PLACEHOLDER_IMG : movie.poster}"
                    class="movie-poster" alt="${movie.name}">
                <div class="card-body d-flex flex-column overlay">
                    <h5 class="card-title">${movie.name}</h5>
                    <p class="card-text text-light small">
                        ⭐ ${movie.rating || 'N/A'}
                    </p>
                </div>
            </div>
        `;

        col.querySelector('.movie-card').addEventListener('click', () => {
            loadMovieDetails(movie.id);
        });

        grid.appendChild(col);
    });
}

async function loadMovieDetails(id) {
    console.log("loadMovieDetails() called");

    document.querySelector('.hero-header').style.display = 'none';
    document.getElementById('movie-results-section').style.display = 'none';

    const contentDiv = document.getElementById('main-content');

    showSpinner(true);
    contentDiv.style.display = 'block';
    try {
        const movieResponse = await fetch(`/getMovie/${id}`);
        if (!movieResponse.ok) throw new Error("Errore nella ricerca dei film");

        const movie = await movieResponse.json();
        console.log("Movie details"+movie);
        currentMovieId = movie.id;

        let reviews = [];
        try {
            if (movie.name) {
                const reviewsResponse = await fetch(`/searchReviews?title=${encodeURIComponent(movie.name)}`);
                if (reviewsResponse.ok) {
                    reviews = await reviewsResponse.json();
                    console.log(reviews);
                }
            }
        } catch (reviewError) {
            console.warn("Errore nella ricerca delle reviews:", reviewError);
        }

        let messages=[];
        try {
            if(movie.id){
                const chatResponse = await fetch(`/loadChat?movieId=${encodeURIComponent(movie.id)}`);
                if (chatResponse.ok){
                    messages = await chatResponse.json();
                    console.log(messages);
                }
            }
        }catch (chatError) {
            console.warn("Errore nel caricamento della chat:", chatError);
        }

        contentDiv.innerHTML = `
            <div class="animate-fade-in">
                <button id="btn_back" class="btn btn-outline-secondary btn-sm mb-4 d-flex justify-content-start" onclick="location.reload()">← Back</button>
                
                <div class="row">
                    <div class="col-md-8">
                        <h1 class="display-5">${movie.name} <small class="text-secondary">(${movie.date})</small></h1>
                        <p class="lead italic">"${movie.tagline}"</p>
                        <hr>
                        <h5 class="text-white mb-3 border-start border-4 border-danger ps-2">Summary</h5>
                        <p>${movie.description}</p>
                        <div class="row mt-4">
                            <div class="col-md-12 mb-3 cast-container">
                                <h5 class="text-white mb-3 border-start border-4 border-danger ps-2">Cast</h5>
                                <div class="d-flex flex-wrap gap-2">
                                ${movie.actors && movie.actors.length > 0
                                        ? movie.actors.slice(0, 15).map(actor =>
                                            `<span class="cast-chip">${actor.name}</span>`
                                        ).join('')
                                        : '<span class="text-muted fst-italic">No cast info.</span>'}
                                
                                <span id="extra-cast" class="d-none" style="display: contents;">
                                    ${movie.actors && movie.actors.length > 15
                                        ? movie.actors.slice(15).map(actor =>
                                            `<span class="cast-chip animate-fade-in">${actor.name}</span>`
                                        ).join('')
                                        : ''}
                                </span>
                            
                                ${movie.actors && movie.actors.length > 15
                                        ? `<span class="btn btn-outline-light btn-sm rounded-pill px-3 py-2" 
                                             style="cursor: pointer;" 
                                             onclick="toggleCast(this)">
                                             +${movie.actors.length - 15} more
                                       </span>`
                                        : ''}
                            </div>
                            </div>
                            <div class="mt-4 mb-4">
                                <h5 class="text-white mb-3 border-start border-4 border-danger ps-2" >Production Credits</h5>
                                <div class="row g-3">
                                    ${movie.crew && movie.crew.length > 0
                                        ? movie.crew.slice(0, 6).map(c => `
                                            <div class="col-6 col-md-4">
                                                <div class="crew-item">
                                                    <span class="crew-role">${c.role}</span>
                                                    <span class="crew-name">${c.name}</span>
                                                </div>
                                            </div>
                                        `).join('')
                                        : '<div class="col-12 text-muted">No crew info available.</div>'}
                                </div>
                                <div id="extra-crew" class="row g-3 mt-0 d-none">
                                    ${movie.crew && movie.crew.length > 6
                                        ? movie.crew.slice(6).map(c => `
                                            <div class="col-6 col-md-4 animate-fade-in">
                                                <div class="crew-item">
                                                    <span class="crew-role">${c.role}</span>
                                                    <span class="crew-name">${c.name}</span>
                                                </div>
                                            </div>
                                        `).join('')
                                        : ''}
                                </div>
                                ${movie.crew && movie.crew.length > 6 ? `
                                    <div class="col-12 mt-3">
                                        <button onclick="toggleCrew(this)" 
                                                class="btn btn-outline-secondary btn-sm w-100 rounded-pill"
                                                style="border-color: #333; color: #aaa;">
                                            Show Full Crew ▼
                                        </button>
                                    </div>
                                ` : ''}
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 text-center">
                        <img src="${movie.poster?.link || PLACEHOLDER_IMG}"
                             class="img-fluid rounded shadow" 
                             alt="${movie.name}"
                             style="width: 100%; height: auto;">
                        <div class="mt-4">
                            <span class="badge bg-info text-dark fs-5 p-2 me-2">Durata: ${movie.minute} min</span>
                            <span class="badge bg-warning text-dark fs-5 p-2">★ ${movie.rating || 'N/A'}</span>
                        </div>
                    </div>
                </div>

                <div class="row mt-5">
                    <div class="col-12">
                        <h4>Reviews (from Rotten Tomatoes)</h4>
                        <div class="list-group mt-3">
                            ${reviews.length > 0 ? reviews.map(r => `
                                <div class="list-group-item mt-4 border-start border-2 ${r.review_type === 'Fresh' ? 'border-success' : 'border-danger'}">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h6 class="mb-1">${r.critic_name} <small class="text-muted">from ${r.publisher_name}</small></h6>
                                        <span class="badge ${r.review_type === 'Fresh' ? 'bg-success' : 'bg-danger'}">${r.review_type}</span>
                                    </div>
                                    ${r.review_content
                                    ? `<p class="mb-1 small fst-italic">"${r.review_content}"</p>`
                                    : ''}
                                </div>
                            `).join('') : '<p class="text-muted">No reviews found for this title.</p>'}
                        </div>
                    </div>
                </div>

                <button id="chat-fab" onclick="toggleChat()"
                    class="btn btn-danger rounded-circle shadow-lg d-flex align-items-center justify-content-center"
                    style="position: fixed; bottom: 30px; right: 30px; width: 60px; height: 60px; z-index: 100; font-size: 28px; transition: transform 0.2s;">
                💬
                </button>

                <div id="chat-window" class="card shadow-lg d-none animate-fade-in"
                 style="position: fixed; bottom: 100px; right: 30px; width: 350px; height: 500px; z-index: 9999; border-radius: 15px; border: none; display: flex; flex-direction: column; overflow: hidden;">

                <div class="card-header text-white d-flex justify-content-between align-items-center"
                     style="background-color: #E50914; padding: 15px;">
                    <span class="fw-bold"> Chat Room: ${movie.name}</span>
                    <button type="button" class="btn-close btn-close-white" onclick="toggleChat()"></button>
                </div>

                <div class="card-body p-0" style="flex: 1; background-color: #f0f0f0; position: relative;">
                    <div id="chat-box" style="height: 100%; overflow-y: auto; padding: 15px; display: flex; flex-direction: column; gap: 10px;">
                        <div class="text-center text-muted small my-2">
                            Welcome to the room!<br>Be nice to others.
                        </div>
                    </div>
                </div>

                <div class="p-3 bg-white border-top">
                    <input type="text" id="chat-username" class="form-control form-control-sm mb-2" placeholder="Your Name" value="Guest">
                    <div class="input-group">
                        <input type="text" id="chat-input" class="form-control" placeholder="Type a message...">
                        <button class="btn btn-danger" onclick="sendMessage('${movie.id}')">➤</button>
                    </div>
                </div>
            </div>
        `;

        showSpinner(false);

        messages.forEach(msg => {
            displayMessage({
                user: msg.user,
                text: msg.text,
                time: msg.timestamp
            });
        });

        try {
            if (!socket) {
                socket = io();
                socket.on('message', (movieId, user, text, time) => {
                    console.log(`Incoming message for room ${movieId}. Current room is ${currentMovieId}`);
                    if (String(movieId) === String(currentMovieId)) {
                        displayMessage({ user: user, text: text, time: time });
                    }
                });
            }
            console.log(`Joining room: ${movie.id}`);
            socket.emit('join_room', movie.id);
        }catch (error) {
            console.error("Socket error:", error);
        }
    } catch (error) {
        console.error(error);
        contentDiv.innerHTML = `<div class="alert alert-danger">Failed to load movie details.</div>`;
    }
}


function sendMessage(movieId) {
    const inputField = document.getElementById('chat-input');
    const userField = document.getElementById('chat-username');

    const text = inputField.value;
    const user = userField.value || "Anonymous";
    const time = new Date().toLocaleTimeString();

    if (text.trim() !== "") {
        socket.emit('message', movieId, user, text, time);

        //displayMessage({ user: user, text: text, time: time });

        inputField.value = "";
    }else{
        inputField.value = "";

        inputField.placeholder = "Cannot send empty message!";
        inputField.classList.add('is-invalid');

        setTimeout(() => {
            inputField.placeholder = "Scrivi un messaggio...";
            inputField.classList.remove('is-invalid');
        }, 2000);
    }
}

function displayMessage(data) {
    const chatBox = document.getElementById('chat-box');
    if (chatBox) {
        const messageHTML = `
            <div class="mb-2 text-center">
                <strong>${data.user}</strong> <span class="text-muted small">[${data.time}]</span>:
                <span>${data.text}</span>
            </div>
        `;
        chatBox.insertAdjacentHTML('beforeend', messageHTML);
        chatBox.scrollTop = chatBox.scrollHeight;
    }
}