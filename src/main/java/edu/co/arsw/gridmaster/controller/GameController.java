package edu.co.arsw.gridmaster.controller;

import edu.co.arsw.gridmaster.model.User;
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

    // GET Requests

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames(){
        return new ResponseEntity<>(gameService.getAllGames(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameByCode(@PathVariable Integer code){
        try {
            return new ResponseEntity<>(gameService.getGameByCode(code), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST Requests

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGame() {
        try {
            return new ResponseEntity<>(gameService.createGame(), HttpStatus.CREATED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // PUT REQUESTS

    @RequestMapping(value = "{code}/users", method = RequestMethod.PUT)
    public ResponseEntity<?> addUser(@PathVariable Integer code,
                                     @RequestBody User newUser){
        try {
            gameService.addUser(code, newUser);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // DELETE Requests

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