package org.firstinspires.ftc.teamcode.utility;

/**
 * Simplifies value toggles using gamepad buttons.
 */
public class Button {

    private boolean pressable = true;
    private boolean state;

    public Button(boolean state) {
        this.state = state;
    }

    /**
     * Returns the value of the button.
     * @return a boolean
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * Updates the button and runs through logic.
     * @param buttonPressed the gamepad button value
     */
    public void update(boolean buttonPressed) {
        if (buttonPressed && pressable) {
            pressable = false;
            state = !state;
        }
        if (!buttonPressed) {
            pressable = true;
        }
    }

}
