package edu.vt.ece5574.roomconditions;

import edu.vt.ece5574.sim.Simulation;
import java.util.concurrent.atomic.AtomicInteger;
import sim.engine.SimState;

// -------------------------------------------------------------------------
/**
 *  Weight class to be used by weight sensor.
 *
 *  @author Stephanie
 *  @version Apr 18, 2016
 */
public class Weight
{

    protected String buildingID = "0"; //a building will have the same ID as building ID
    protected String roomID;

    private AtomicInteger weight;
    private Simulation state;

    public Weight(SimState state_){

        state = (Simulation)state_;
        weight = new AtomicInteger(0);
    }

    public Weight(int weight_,SimState state_){

        state = (Simulation)state_;
        weight = new AtomicInteger(weight_);
    }

    public int getWeight(){

        return weight.get();
    }

    public void setWeight(int weight_) {

        weight.set(weight_);
    }

}
