var game = (function(){
    const createBt = document.getElementById('creat');
    const joinBt = document.getElementById('join');

    if (createBt){
        createBt.addEventListener('click', function() {
            window.location.href = "game.html";
        });
    }

})();