package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.CANdle.LEDStripType;

import frc.robot.Constants; 

public class FlashyLights {
    CANdle lights = new CANdle(Constants.Swerve.CandleID);

    CANdleConfiguration config = new CANdleConfiguration();

    public void configLights() {
        config.stripType = LEDStripType.RGB; // set the strip type to RGB
        config.brightnessScalar = 1; // dim the LEDs to half brightness
        lights.configAllSettings(config);
        lights.setLEDs(0,255,0);
    }

    public void setLights(int r, int g, int b) {
        lights.setLEDs(r, g, b);
    }

    // public void randomLights() {

    //     int num1 = (int)((Math.random() * (255 - 0 + 1) + 0));
    //     int num2 = (int)((Math.random() * (255 - 0 + 1) + 0));
    //     int num3 = (int)((Math.random() * (255 - 0 + 1) + 0));

    //     lights.setLEDs(num1, num2, num3);
        
    // }
}
