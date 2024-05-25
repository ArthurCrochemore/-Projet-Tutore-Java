package fr.univtours.polytech.punchingmanagement.controller;

import java.time.LocalTime;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class TimeSpinner extends Spinner<LocalTime> {
    private boolean roundTo15Minutes = true;

    public TimeSpinner(LocalTime time) {
        StringConverter<LocalTime> converter = new StringConverter<LocalTime>() {

            @Override
            public String toString(LocalTime time) {
                if (time == null)
                    return "";
                return TimeUtils.format(time);
            }

            @Override
            public LocalTime fromString(String string) {
                LocalTime time = TimeUtils.stringToLocalTime(string);
                if (roundTo15Minutes)
                    time = TimeUtils.roundTo15Minutes(time);
                return time;
            }
        };

        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            @Override
            public void decrement(int steps) {
                LocalTime time = getValue();
                if (time == null)
                    time = TimeUtils.getCurrentRoundedLocalTime();
                else {
                    time = time.minusMinutes(15L * steps);
                }
                setValue(time);
            }

            @Override
            public void increment(int steps) {
                LocalTime time = getValue();
                if (time == null)
                    time = TimeUtils.getCurrentRoundedLocalTime();
                else {
                    time = time.plusMinutes(15L * steps);
                }
                setValue(time);
            }
        };
        valueFactory.setConverter(converter);
        valueFactory.setValue(time);

        this.setValueFactory(valueFactory);
    }

    public TimeSpinner() {
        this(LocalTime.now());
    }

    public void setValue(LocalTime time) {
        this.getValueFactory().setValue(time);
    }

    public void setRoundTo15Minutes(boolean roundTo15Minutes) {
        this.roundTo15Minutes = roundTo15Minutes;
    }
}