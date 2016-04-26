package edu.vt.ece5574.agents;

import edu.vt.ece5574.roomconditions.Weight;
import edu.vt.ece5574.sim.Simulation;
import sim.engine.SimState;

// -------------------------------------------------------------------------
/**
 *  Class for weight sensors.
 *
 *  @author Stephanie
 *  @version Apr 18, 2016
 */
public class WeightSensor extends Sensor
{

    private static final long serialVersionUID = 1;
    int x,y;
    Building bld;
    Weight weightInKg;

    // ----------------------------------------------------------
    /**
     * Create a new WeightSensor object.
     * @param id_
     * @param buildingID_
     * @param sensorType_
     */
    public WeightSensor(String id_, String buildingID_, String sensorType_)
    {
        super(id_, buildingID_, "weight");
    }

    // ----------------------------------------------------------
    /**
     * Create a new WeightSensor object.
     * @param id_
     * @param buildingID_
     * @param state_
     * @param x_
     * @param y_
     */
    public WeightSensor(String id_, String buildingID_,SimState state_, int x_, int y_) {

        super(id_,buildingID_,"weight");
        Simulation state = (Simulation)state_;
        x = x_;
        y = y_;
        bld = (Building)state.getAgentByID(buildingID);
    }


}
