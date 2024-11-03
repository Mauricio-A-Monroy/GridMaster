package edu.co.arsw.gridmaster.controller;

import edu.co.arsw.gridmaster.model.Player;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.Tuple;
import edu.co.arsw.gridmaster.service.GridMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
public class GridMasterController {

    @Autowired
    GridMasterService gridMasterService;

    // GET Requests

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames(){
        return new ResponseEntity<>(gridMasterService.getAllGames(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameByCode(@PathVariable Integer code){
        try {
            return new ResponseEntity<>(gridMasterService.getGameByCode(code), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{code}/score", method = RequestMethod.GET)
    public ResponseEntity<?> getScoreboardByCode(@PathVariable Integer code){
        try {
            return new ResponseEntity<>(gridMasterService.getScoreBoard(code), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{code}/players", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPlayers(@PathVariable Integer code){
        try {
            return new ResponseEntity<>(gridMasterService.getPlayers(code), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{code}/players/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByName(@PathVariable Integer code,
                                             @PathVariable String name){
        try {
            return new ResponseEntity<>(gridMasterService.getPlayerByName(code, name), HttpStatus.ACCEPTED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST Requests

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGame() {
        try {
            return new ResponseEntity<>(gridMasterService.createGridMaster(), HttpStatus.CREATED);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // PUT REQUESTS

    @RequestMapping(value = "{code}/start", method = RequestMethod.PUT)
    public ResponseEntity<?> startGame(@PathVariable Integer code){
        try {
            gridMasterService.startGame(code);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "{code}/players", method = RequestMethod.PUT)
    public ResponseEntity<?> addPlayer(@PathVariable Integer code,
                                     @RequestBody Player player){
        try {
            gridMasterService.addPlayer(code, player.getName());
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "{code}/players/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> movePlayer(@PathVariable Integer code,
                                        @PathVariable String name,
                                        @RequestBody Tuple<Integer, Integer> newPosition){
        try {
            gridMasterService.move(code, name, newPosition);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // DELETE Requests

    @RequestMapping(value = "{code}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@PathVariable Integer code){
        try {
            gridMasterService.deleteGridMaster(code);
        } catch (GridMasterException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
