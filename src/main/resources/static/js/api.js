api=(function(){
    var createGame = function(playerName) {
        return $.ajax({
            url: 'http://localhost:8080/games',
            type: 'POST',
            contentType: "application/json"
        }).then(function(gameCode) {
            localStorage.setItem('gameCode', gameCode);
            return addPlayer(gameCode, playerName);
            
        }).catch(function(error) {
            console.error("Error creating game:", error);
        });
    };

    var addPlayer = function(gameCode, playerName) {
        return $.ajax({
            url: 'http://localhost:8080/games/' + gameCode + '/player',
            type: 'PUT',
            data: JSON.stringify({ name: playerName }),
            contentType: "application/json"
        }).then(function(response) {
            console.log("Player added:", response);
            return response;
        }).catch(function(error) {
            console.error("Error adding player:", error);
        });
    };

    return {
        createGame: createGame,
        addPlayer: addPlayer
    };
})();