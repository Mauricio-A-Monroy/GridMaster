package edu.co.arsw.gridmaster.persistance;

import edu.co.arsw.gridmaster.model.GridMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridMasterRepository extends MongoRepository<GridMaster, Integer> {

}