/* Sim_event.java */

package org.cloudbus.cloudsim.core;


/**
 * This class represents a simulation event which is passed between
 * the entities in the simulation.
 *
 * @see Simulation
 * @see SimEntity

 * @author Costas Simatos
 */
public class SimEvent implements Cloneable, Comparable<SimEvent> {
	private final int etype; // internal event type
	private final double time; // time at which event should occur
	private double endWaitingTime; // time that the event was removed from the
									// queue for service
	private int entSrc; // id of entity who scheduled event
	private int entDst; // id of entity event will be sent to
	private final int tag; // the user defined type of the event
	private final Object data; // any data the event is carrying
	private long serial = -1;

	// Internal event types
	public static final int ENULL = 0;
	public static final int SEND = 1;
	public static final int HOLD_DONE = 2;
	public static final int CREATE = 3;

	/**
	 * Create a blank event.
	 */
	public SimEvent() {
		etype = ENULL;
		this.time = -1L;
		endWaitingTime = -1.0;
		entSrc = -1;
		entDst = -1;
		this.tag = -1;
		data = null;
	}

	// ------------------- PACKAGE LEVEL METHODS --------------------------
	SimEvent(int evtype, double time, int src, int dest, int tag, Object edata) {
		etype = evtype;
		this.time = time;
		entSrc = src;
		entDst = dest;
		this.tag = tag;
		data = edata;
	}

	SimEvent(int evtype, double time, int src) {
		etype = evtype;
		this.time = time;
		entSrc = src;
		entDst = -1;
		this.tag = -1;
		data = null;
	}

	protected void setSerial(long serial) {
		this.serial = serial;
	}

	// Used to set the time at which this event finished waiting in the event
	// queue. This is used for statistical purposes.
	protected void setEndWaitingTime(double end_waiting_time) {
		this.endWaitingTime = end_waiting_time;
	}

	@Override
	public String toString() {
		return "Event tag = " + tag + " source = "
				+ CloudSim.getEntity(this.entSrc).getName()
				+ " destination = "
				+ CloudSim.getEntity(this.entDst).getName();
	}

	// The internal type
	public int getType() {
		return etype;
	}

	// ------------------- PUBLIC METHODS --------------------------

	/**
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(SimEvent event) {
		if (event == null) {
			return 1;
		} else if (time < event.time) {
			return -1;
		} else if (time > event.time) {
			return 1;
		} else if (serial < event.serial) {
			return -1;
		} else if (this == event) {
			return 0;
		} else {
			return 1;
		}
	}


	/**
	 * Get the unique id number of the entity which received this event.
	 * @return the id number
	 */
	public int getDestination() {
		return entDst;
	}

	/**
	 * Get the unique id number of the entity which scheduled this event.
	 * @return the id number
	 */
	public int getSource() {
		return entSrc;
	}

	/**
	 * Get the simulation time that this event was scheduled.
	 * @return The simulation time
	 */
	public double eventTime() {
		return time;
	}

	/**
	 * Get the simulation time that this event was removed from the queue for
	 * service.
	 * @return The simulation time
	 */
	public double endWaitingTime() {
		return endWaitingTime;
	}

	/**
	 * Get the user-defined tag of this event
	 * @return The tag
	 */
	public int type() {
		return tag;
	}

	/**
	 * Get the unique id number of the entity which scheduled this event.
	 * @return the id number
	 */
	public int scheduledBy() {
		return entSrc;
	}

	/**
	 * Get the user-defined tag of this event.
	 * @return The tag
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * Get the data passed in this event.
	 * @return A reference to the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Create an exact copy of this event.
	 * @return The event's copy
	 */
	@Override
	public Object clone() {
		return new SimEvent(etype, time, entSrc, entDst, tag, data);
	}

	/**
	 * Set the source entity of this event.
	 * @param s The unique id number of the entity
	 */
	public void setSource(int s) {
		entSrc = s;
	}

	/**
	 * Set the destination entity of this event.
	 * @param d The unique id number of the entity
	 */
	public void setDestination(int d) {
		entDst = d;
	}
}
