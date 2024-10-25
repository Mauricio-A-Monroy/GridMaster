class Game {
    constructor() {
        // Elementos del DOM
        this.joinButton = document.getElementById('join');
        this.gameIdInput = document.getElementById('gameIdInput');

        // Inicializar eventos
        this.initEvents();
    }

    initEvents() {
        // Agregar el evento de clic al botón de unirse
        this.joinButton.addEventListener('click', () => this.showGameIdInput());
    }

    showGameIdInput() {
        this.gameIdInput.style.display = 'block'; // Cambia a 'block' para mostrar el input
    }
}

// Instanciar la clase Game cuando se cargue la página
const app = new Game();
