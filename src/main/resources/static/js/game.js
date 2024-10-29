var game = (function() {
    const board = document.getElementById('board');
    const rows = 50;
    const columns = 50;
    const cellSize = 10;
    let playerRow = 25; 
    let playerColumn = 30; 
    const playerColor = "#FFA500";
    const grid = Array.from({ length: rows }, () => Array(columns).fill(null));

    var setPlayerColor = function(gameCode, playerName) {
        api.getPlayer(gameCode, playerName).then(function(player) {
            // Convertir RGB a hexadecimal
            const rgb = player.color; // [255, 0, 0]
            const hexColor = rgbToHex(rgb[0], rgb[1], rgb[2]);
            playerColor = hexColor;
            console.log("Player color in hex:", playerColor);
        });
    };
    
    function rgbToHex(r, g, b) {
        return "#" + [r, g, b].map(x => x.toString(16).padStart(2, "0")).join("");
    }
    
    var loadBoard = function() {
        console.log("rows: ", rows, " columns: ",columns)
        board.style.setProperty('--rows', rows);
        board.style.setProperty('--columns', columns);

        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                const cell = document.createElement('div');
                cell.classList.add('cell');

                // Almacena la referencia de la celda en la matriz
                grid[i][j] = cell;

                
                if (i === playerRow && j === playerColumn) {
                    console.log("PlayerColor: ", playerColor);
                    board.style.setProperty('--playarColor', playerColor);
                    const hexagon = document.createElement('div');
                    hexagon.classList.add('hexagon');
                    cell.appendChild(hexagon);
                }

                board.appendChild(cell);
            }
        }
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

    return {
        loadBoard,
        setPlayerColor
    };

})();

document.addEventListener('DOMContentLoaded', function() {
    game.loadBoard();
});
