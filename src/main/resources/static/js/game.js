var game = (function() {
    const board = document.getElementById('board');
    const rows = 100;
    const columns = 100;
    var playerName = "";
    let playerRow = 50; 
    let playerColumn = 20; 
    var playerColor = "#FFA500";
    var gameCode = -1;

    const grid = Array.from({ length: rows }, () => Array(columns).fill(null));
    var stompClient = null;
    var competitors = [];

    var setPlayerColor = function(gameCode, playerName) {
        return api.getPlayer(gameCode, playerName).then(function(player) {
            const rgb = player.color; // [255, 0, 0]
            const hexColor = rgbToHex(rgb[0], rgb[1], rgb[2]);
            playerColor = hexColor;
            console.log("Player color in hex:", playerColor);
            loadCompetitorsFromServer();
            return playerColor;
        });
    };

    var setPlayerName = function(newPlayerName){
        playerName = newPlayerName;
    }

    var setGameCode = function(newCode){
        gameCode = newCode;
    }
    
    function rgbToHex(r, g, b) {
        return "#" + [r, g, b].map(x => x.toString(16).padStart(2, "0")).join("");
    }
    
    var loadBoard = function() {
        console.log("rows: ", rows, " columns: ",columns)
        board.style.setProperty('--rows', rows);
        board.style.setProperty('--columns', columns);
        console.log("PlayerColor: ", playerColor);
        board.style.setProperty('--playarColor', playerColor);

        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                const cell = document.createElement('div');
                cell.classList.add('cell');

                // Almacena la referencia de la celda en la matriz
                grid[i][j] = cell;

                
                if (i === playerRow && j === playerColumn) {
                    const hexagon = document.createElement('div');
                    hexagon.classList.add('hexagon');
                    cell.appendChild(hexagon);
                }

                board.appendChild(cell);
            }
        }

        const gameCode = localStorage.getItem('gameCode');

        return api.getScore(gameCode).then(function(players) {
            console.log(players);
            updateScoreBoard(players);
        });
    };

    var updateScoreBoard = function(players) {
        const scoreTableBody = document.getElementById('scoreTableBody');
        scoreTableBody.innerHTML = ""; // Limpia las filas anteriores

        Object.entries(players).forEach(([player, score], index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${player}</td>
                <td>${score}</td>
            `;
            scoreTableBody.appendChild(row);    
        });
    };

    document.addEventListener('keydown', function(event) {
        switch (event.key) {
            case 'ArrowUp':
                movePlayer('up');
                break;
            case 'ArrowDown':
                movePlayer('down');
                break;
            case 'ArrowLeft':
                movePlayer('left');
                break;
            case 'ArrowRight':
                movePlayer('right');
                break;
        }
    });
    
    var movePlayer = function(direction) {
        var newRow = playerRow;
        var newColumn = playerColumn;

        if (direction === 'up' && playerRow > 0) {
            newRow--;
        } else if (direction === 'down' && playerRow < rows - 1) {
            newRow++;
        } else if (direction === 'left' && playerColumn > 0) {
            newColumn--;
        } else if (direction === 'right' && playerColumn < columns - 1) {
            newColumn++;
        }

        
        positionPlayer(newRow, newColumn); 
        
        console.log("moving player: ", gameCode, this.playerName, newRow, newColumn);

        api.move(gameCode, playerName, newRow, newColumn);

        return api.getScore(gameCode).then(function(players) {
            console.log(players);
            updateScoreBoard(players);
        }).then(function() {
            // Enviar mensaje al tópico
            stompClient.send('/topic/player/' + localStorage.getItem('playerName'), {}, JSON.stringify("Message"));
        });
    };

    var positionPlayer = function(newRow, newColumn) {
        const previousCell = grid[playerRow][playerColumn];
        const previousHexagon = previousCell.querySelector('.hexagon');
        if (previousHexagon) {
            previousCell.removeChild(previousHexagon);
        }
        captureCells(previousCell);

        playerRow = newRow;
        playerColumn = newColumn;

        // Encuentra la nueva celda y añade el hexágono
        const currentCell = grid[playerRow][playerColumn];
        const hexagon = document.createElement('div');
        hexagon.classList.add('hexagon');
        currentCell.appendChild(hexagon);
    };

    var captureCells = function(celda) {
        celda.style.backgroundColor = playerColor;
        // Aquí puedes añadir la lógica para sumar puntos o cambiar el turno
    };

    var loadCompetitorsFromServer = function () {
        api.getPlayers(localStorage.getItem('gameCode')).then(function(data) {
            competitors = data;
            console.log("players", competitors);
            competitors.forEach(
                function (p) {
                    console.log("Loading competitors....");
                    console.log(competitors);
                    connectAndSubscribeToCompetitors();
                }
            );
        });
    };

    function connectAndSubscribeToCompetitors() {
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        console.log("Connecting...");
        console.log(stompClient);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            competitors.forEach(
                    function(p){
                        stompClient.subscribe('/topic/player/' + p.name, function (data) {
                            msgdata = JSON.parse(data.body);
                            console.log(msgdata);
                            // paintBoard(); Pintar el tablero completo pero creo que puede ser demorado aunque vamos a la segura.
                            // paintCompetitors(); Obtener la posición de cada jugador y pintarlo? Pero no sé cómo mostrar el trazo de los otros jugadores
                        });
                    }
            );
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    return {
        loadBoard,
        setPlayerColor,
        setPlayerName,
        setGameCode
    };

})();

document.addEventListener('DOMContentLoaded', function() {
    
    var params = new URLSearchParams(window.location.search);

    var playerName = params.get('playerName');
    var gameCode = params.get('gameCode');
    game.setPlayerName(playerName);
    game.setGameCode(gameCode);

    const gameCodeElement = document.getElementById('gameCode');
    
    if (gameCode && gameCodeElement) {
        gameCodeElement.textContent = gameCode;
    } else {
        console.error("El elemento gameCode no se encontró o el código de partida está ausente.");
    }
    
    if (gameCode && playerName) {
        game.setPlayerColor(gameCode, playerName).then(() => {
            game.loadBoard();
        });
    } else {
        console.error("Game code or player name is missing.");
    }

});
