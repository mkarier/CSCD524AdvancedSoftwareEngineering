package cs524.controlsystem;

//=============================================================================================================================================================
/**
 * Defines an arbitrary physical agent (assumed to be an airplane in CS 524 Task 3) in two-dimensional space. It starts at a position with a pitch and speed.
 * From there, only putative rudder can be controlled, which then changes the pitch per update. The new position is based on the change in position from the old
 * position with respect to the current speed and pitch.
 * 
 * Do not change this file.
 * 
 * @author Dan Tappan
 */
public class Agent implements I_Updateable
{
   /** the plus/minus band for accepting that the current pitch has reached the target pitch */
   private static final double EPSILON = 0.1;

   /** the positive and negative limit to pitch delta */
   private static final double PITCH_DELTA_LIMIT = 5;

   /** for noninstantaneous deflection, the current pitch updates toward the target pitch by this value once per update */
   private static final double PITCH_DELTA_DELTA = 0.1;

   /** the degrees in a circle */
   private static final double CIRCLE_DEGREES = 360;

   /** the x position of the agent */
   private double _x;

   /** the y position of the agent */
   private double _y;

   /** the current speed of the agent */
   private final double _speed;

   /** the current pitch of the agent */
   private double _pitch;

   /** the current signed angle on [-PITCH_DELTA_LIMIT,+PITCH_DELTA_LIMIT] by which the pitch changes per update */
   private double _pitchDelta;

   /** the desired signed angle on [-PITCH_DELTA_LIMIT,+PITCH_DELTA_LIMIT] by which the pitch changes per update */
   private double _pitchDeltaTarget;

   /** whether deflection to a new pitch delta is instantaneous, otherwise incremental */
   private final boolean _isDeflectionInstantaneous;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * @param x - the <i>x</i> position of the agent
    * @param y - the <i>y</i> position of the agent
    * @param speed - the current speed of the agent
    * @param pitch - the current pitch of the agent
    * @param isDeflectionInstantaneous - whether deflection to a new pitch delta is instantaneous, otherwise incremental
    */
   public Agent(final double x, final double y, final double speed, final double pitch, final boolean isDeflectionInstantaneous)
   {
      Assert.nonnegative(speed);
      Assert.range(pitch, -90, +90);

      _x = x;
      _y = y;

      _speed = speed;

      _pitch = pitch;
      _pitchDelta = 0;
      _pitchDeltaTarget = 0;
      _isDeflectionInstantaneous = isDeflectionInstantaneous;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the current pitch of the agent.
    * 
    * @return the pitch
    */
   public double getPitch()
   {
      return _pitch;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the current signed angle on [-PITCH_DELTA_LIMIT,+PITCH_DELTA_LIMIT] by which the pitch changes per update.
    * 
    * @return the angle
    */
   public double getPitchDelta()
   {
      return _pitchDelta;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the desired signed angle on [-PITCH_DELTA_LIMIT,+PITCH_DELTA_LIMIT] by which the pitch changes per update.
    * 
    * @return the angle
    */
   public double getPitchDeltaTarget()
   {
      return _pitchDeltaTarget;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the current speed of the agent.
    * 
    * @return the speed
    */
   public double getSpeed()
   {
      return _speed;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the state of the agent in string comma-separated-values form.
    * 
    * @return the state
    */
   public String getStateCSV()
   {
      return (_x + "," + _y + "," + _speed + "," + _pitch + "," + _pitchDelta + "," + _pitchDeltaTarget + "," + _isDeflectionInstantaneous);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the state of the agent in string comma-separated-values form.
    * 
    * @return the state
    */
   public String getStateGnuplot()
   {
      // xxx add vector barbs

      return (_x + " " + _y);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the <i>x</i> position of the agent.
    * 
    * @return the position
    */
   public double getX()
   {
      return _x;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the <i>y</i> position of the agent.
    * 
    * @return the position
    */
   public double getY()
   {
      return _y;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets whether deflection to a new pitch delta is instantaneous, otherwise incremental.
    * 
    * @return the result
    */
   public boolean isDeflectionInstantaneous()
   {
      return _isDeflectionInstantaneous;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Determines whether the agent is within a certain distance of a point.
    * 
    * @param x - the <i>x</i> position of the point
    * @param y - the <i>y</i> position of the point
    * @param distance - the distance threshold
    * 
    * @return the result
    */
   public boolean isProximate(final double x, final double y, final double distance)
   {
      Assert.positive(distance);

      double distanceX = (_x - x);
      double distanceY = (_y - y);

      double distanceActual = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

      boolean isProximate = (distanceActual <= distance);

      return isProximate;
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Sets the desired signed angle on [-PITCH_DELTA_LIMIT,+PITCH_DELTA_LIMIT] by which the pitch changes per update. If the deflection mode is instantaneous,
    * then this angle is achieved immediately; otherwise, the current angle updates toward the desired angle by <code>PITCH_DELTA_DELTA</code>.
    * 
    * @param pitchDelta - the angle
    */
   public void setPitchDeltaTarget(final double pitchDelta)
   {
      Assert.range(pitchDelta, -PITCH_DELTA_LIMIT, PITCH_DELTA_LIMIT);

      _pitchDeltaTarget = pitchDelta;

      if (_isDeflectionInstantaneous)
      {
         _pitchDelta = _pitchDeltaTarget;
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the state of the agent in string form.
    */
   @Override
   public String toString()
   {
      return ("Agent{x=" + _x + " y=" + _y + " speed=" + _speed + " pitch=" + _pitch + " pitchDelta=" + _pitchDelta + " pitchDeltaTarget=" + _pitchDeltaTarget
            + " isDeflectionInstantaneous=" + _isDeflectionInstantaneous + "}");
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * {@inheritDoc}
    */
   @Override
   public void update_()
   {
      updatePitch();

      updatePosition();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Updates the pitch of the agent by one delta toward the target.
    */
   private void updatePitch()
   {
      if (!_isDeflectionInstantaneous)
      {
         if (_pitchDelta < (_pitchDeltaTarget - EPSILON))
         {
            _pitchDelta += PITCH_DELTA_DELTA;
         }
         else if (_pitchDelta > (_pitchDeltaTarget + EPSILON))
         {
            _pitchDelta -= PITCH_DELTA_DELTA;
         }
         else
         {
            _pitchDelta = _pitchDeltaTarget;
         }
      }

      Assert.range(_pitchDelta, -PITCH_DELTA_LIMIT, PITCH_DELTA_LIMIT);

      _pitch += _pitchDelta;

      // if (_pitch < 0)
      // {
      // _pitch = (CIRCLE_DEGREES + _pitch);
      // }
      //
      // _pitch %= CIRCLE_DEGREES;

      // System.err.println(_pitch + " " + _pitchDelta);
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Updates the position of the agent based on the current speed and pitch.
    */
   private void updatePosition()
   {
      double pitchRadians = Math.toRadians(_pitch);

      double deltaX = (Math.cos(pitchRadians) * _speed);
      double deltaY = (Math.sin(pitchRadians) * _speed);

      _x += deltaX;
      _y += deltaY;

      // System.err.println(_x + " " + _y + " " + _pitch +" "+ _pitchDelta);
   }
}
