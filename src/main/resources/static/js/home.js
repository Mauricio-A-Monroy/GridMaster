var home = (function(){
    const joinBt = document.getElementById('join');
    const gameIdInput = document.getElementById('gameIdInput');


    var createGame = function(playerName) {
        const errorMessageDiv = document.getElementById('error-message');
    
        // Limpiar el mensaje de error previo
        errorMessageDiv.textContent = '';
        errorMessageDiv.style.display = 'none';

        // Validar que el campo de nombre no esté vacío
        if (!playerName.trim()) {
            errorMessageDiv.textContent = "Pls, enter your name before to create a game";
            errorMessageDiv.style.display = 'block'; // Mostrar el mensaje de error
            return; // Salir de la función si el nombre está vacío
        }

        api.createGame(playerName).then(gameCode => {
            return api.addPlayer(gameCode, playerName); 
        }).then(() => {
            window.location.href = "game.html";
        }).catch(error => {
            console.error("Error al crear el juego o añadir el jugador:", error);
        });
    };


    if (joinBt) {
        joinBt.addEventListener('click', function() {
            gameIdInput.style.display = 'block';
        });
    };

    return {
        createGame
    };

})();