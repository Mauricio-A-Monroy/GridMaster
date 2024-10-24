package edu.co.arsw.gridmaster.controller;

import edu.co.arsw.gridmaster.model.Game;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    GameService gameService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames(){
        return new ResponseEntity<>(gameService.getAllGames(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "code", method = RequestMethod.GET)
    public ResponseEntity<?> getGameByCode(@PathVariable Integer code){
        try {
            return new ResponseEntity<>(gameService.getGameByCode(code), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGame(@RequestBody Game game){
        try {
            gameService.createGame(game);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "{code}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@PathVariable Integer code){
        try {
            gameService.deleteGame(code);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
