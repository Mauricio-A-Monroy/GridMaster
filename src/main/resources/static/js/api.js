api=(function(){
    
    //Gets
    var getPlayer = function(gameCode, playerName) {
        console.log("gameCode: ", gameCode, " playerName: ", playerName);
        return $.ajax({
            url: 'http://localhost:8080/games/' + gameCode + '/players/' + playerName,
            type: 'GET',
            contentType: "application/json"
        }).then(function(response) {
            console.log("Player: ", response);
            return response;
        }).catch(function(error) {
            console.error("Error getting player:", error);
        });
    };
    
    //Post
    var createGame = function(playerName) {
        return $.ajax({
            url: 'http://localhost:8080/games',
            type: 'POST',
            contentType: "application/json"
        }).then(function(gameCode) {
            localStorage.setItem('gameCode', gameCode);
            return gameCode;
        }).catch(function(error) {
            console.error("Error creating game:", error);
        });
    };

    //Puts
    var addPlayer = function(gameCode, playerName) {
        return $.ajax({
            url: 'http://localhost:8080/games/' + gameCode + '/player',
            type: 'PUT',
            data: JSON.stringify({ name: playerName }),
            contentType: "application/json"
        }).then(function(response) {
            console.log("Player added");
            return response;
        }).catch(function(error) {
            console.error("Error adding player:", error);
        });
    };



    return {
        createGame: createGame,
        addPlayer: addPlayer,
        getPlayer: getPlayer
    };
})();