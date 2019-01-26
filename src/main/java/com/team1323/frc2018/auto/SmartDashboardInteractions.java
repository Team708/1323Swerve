package com.team1323.frc2018.auto;

import com.team1323.frc2018.auto.modes.CloseFarBallMode;
import com.team1323.frc2018.auto.modes.StandStillMode;
import com.team1323.frc2018.auto.modes.TwoCloseOneBallMode;
import com.team1323.frc2018.auto.modes.TwoFarOneBallMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInteractions {
    private static final String SELECTED_AUTO_MODE = "selected_auto_mode";
    
    private static final AutoOption DEFAULT_MODE = AutoOption.TWO_CLOSE_ONE_BALL;
    
    private SendableChooser<AutoOption> modeChooser;
    private SendableChooser<Side> sideChooser;
    
    public void initWithDefaults(){
    	modeChooser = new SendableChooser<AutoOption>();
        modeChooser.setDefaultOption(DEFAULT_MODE.name, DEFAULT_MODE);
        modeChooser.addOption(AutoOption.TWO_FAR_ONE_BALL.name, AutoOption.TWO_FAR_ONE_BALL);
        modeChooser.addOption(AutoOption.CLOSE_FAR_BALL.name, AutoOption.CLOSE_FAR_BALL);
        modeChooser.addOption(AutoOption.FAR_CLOSE_BALL.name, AutoOption.FAR_CLOSE_BALL);

        sideChooser = new SendableChooser<Side>();
        sideChooser.setDefaultOption("Left", Side.LEFT);
        sideChooser.addOption("Right", Side.RIGHT);
    	
        SmartDashboard.putData("Mode Chooser", modeChooser);
        SmartDashboard.putData("Side Chooser", sideChooser);
    	SmartDashboard.putString(SELECTED_AUTO_MODE, DEFAULT_MODE.name);
    }
    
    public AutoModeBase getSelectedAutoMode(){
        AutoOption selectedOption = (AutoOption) modeChooser.getSelected();
        Side selectedSide = (Side) sideChooser.getSelected();
        boolean left = (selectedSide == Side.LEFT);
                
        return createAutoMode(selectedOption, left);
    }
    
    public String getSelectedMode(){
    	AutoOption option = (AutoOption) modeChooser.getSelected();
    	return option.name;
    }
    
    enum AutoOption{
        TWO_CLOSE_ONE_BALL("2 Close, 1 Ball"),
        TWO_FAR_ONE_BALL("2 Far, 1 Ball"),
        CLOSE_FAR_BALL("1 Close, 1 Far, 1 Ball"),
        FAR_CLOSE_BALL("1 Far, 1 Close, 1 Ball");
    	
    	public final String name;
    	
    	AutoOption(String name){
    		this.name = name;
    	}
    }

    enum Side{
        LEFT, RIGHT
    }
    
    private AutoModeBase createAutoMode(AutoOption option, boolean left){
    	switch(option){
			case TWO_CLOSE_ONE_BALL:
                return new TwoCloseOneBallMode(left);
            case TWO_FAR_ONE_BALL:
                return new TwoFarOneBallMode(left);
            case CLOSE_FAR_BALL:
                return new CloseFarBallMode(left);
            case FAR_CLOSE_BALL:
                return new CloseFarBallMode(left);
            default:
                System.out.println("ERROR: unexpected auto mode: " + option);
                return new StandStillMode();
    	}
    }
    
    public void output(){
    	SmartDashboard.putString(SELECTED_AUTO_MODE, getSelectedMode());
    }
}
