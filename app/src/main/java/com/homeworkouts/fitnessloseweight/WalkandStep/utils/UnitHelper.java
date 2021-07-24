
package com.homeworkouts.fitnessloseweight.WalkandStep.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.homeworkouts.fitnessloseweight.R;


public class UnitHelper {

    public static int USER_UNIT_FACTOR = 0;

    public static int USER_UNIT_SHORT_DESCRIPTION = 1;

    public static int USER_UNIT_DESCRIPTION = 2;

    public static int USER_UNIT_VELOCITY_DESCRIPTION = 3;

    public static int USER_SMALL_UNIT_FACTOR = 4;

    public static int USER_SMALL_UNIT_SHORT_DESCRIPTION = 5;

    public static double metersToKilometers(double m) {
        return m / 1000;
    }

    public static double metersPerSecondToKilometersPerHour(double ms) {
        return ms * 3.6;
    }


    public static double metersToUsersLengthUnit(double m, Context context) {
        return m;
    }

    public static double kilometerToUsersLengthUnit(double km, Context context) {
        double factor = Double.parseDouble(getUsersUnit(USER_UNIT_FACTOR, context));
        return km * factor;
    }


    public static double kilometerToUsersSmallLengthUnit(double km, Context context) {
        double factor = Double.parseDouble(getUsersUnit(USER_SMALL_UNIT_FACTOR, context));
        return km * factor;
    }

    public static double usersLengthUnitToKilometers(double length, Context context) {
        double factor = Double.parseDouble(getUsersUnit(USER_UNIT_FACTOR, context));
        return length / factor;
    }


    public static double kilometersPerHourToUsersVelocityUnit(double kmh, Context context) {
        double factor = Double.parseDouble(getUsersUnit(USER_UNIT_FACTOR, context));
        return kmh * factor;
    }


    public static String usersLengthDescriptionShort(Context context) {
        return getUsersUnit(USER_UNIT_SHORT_DESCRIPTION, context);
    }

    public static String usersSmallLengthDescriptionShort(Context context) {
        return getUsersUnit(USER_SMALL_UNIT_SHORT_DESCRIPTION, context);
    }

    public static String usersLengthDescriptionForMeters(Context context) {
        return "meters";
    }


    public static String usersVelocityDescription(Context context) {
        return getUsersUnit(USER_UNIT_VELOCITY_DESCRIPTION, context);
    }

    public static String formatKilometersPerHour(double kmh, Context context){
        return formatString("%.2f", kilometersPerHourToUsersVelocityUnit(kmh, context), context) + usersVelocityDescription(context);
    }

    public static FormattedUnitPair formatKilometers(double km, Context context){
        double kilometerInUsersLenghtUnit = kilometerToUsersLengthUnit(km, context);
        if(kilometerInUsersLenghtUnit < 0.1){
            return new FormattedUnitPair(formatString("%.2f", kilometerToUsersSmallLengthUnit(km, context), context), usersSmallLengthDescriptionShort(context));
        }else{
            return new FormattedUnitPair(formatString("%.2f", kilometerInUsersLenghtUnit, context), usersLengthDescriptionShort(context));
        }
    }

    public static FormattedUnitPair formatCalories(double kcal, Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String unit_key = sharedPref.getString(context.getString(R.string.pref_unit_of_energy), "cal");
        if(unit_key.equals("J")) {
            double joule = kcal * 4184;
            if (joule < 100) {
                return new FormattedUnitPair(formatString("%.2f", joule, context), context.getString(R.string.joules));
            } else {
                return new FormattedUnitPair(formatString("%.2f", joule / 1000, context), context.getString(R.string.kilojoules));
            }
        }else {
            if (kcal < 100) {
                return new FormattedUnitPair(formatString("%.2f", kcal / 1000, context), context.getString(R.string.summary_card_calories));
            } else {
                return new FormattedUnitPair(formatString("%.2f", kcal, context), context.getString(R.string.summary_card_kilocalories));
            }
        }
    }

    private static String formatString(String format, double d, Context context){
        return String.format(context.getResources().getConfiguration().locale, format, d);
    }


    public static String getUsersUnit(int type, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String unit_key = sharedPref.getString(context.getString(R.string.pref_unit_of_length), "km");
        String unit;
        switch(unit_key){
            case "mi":
                unit = context.getString(R.string.unit_of_length_mi);
                break;
            case "km":
            default:
                unit = context.getString(R.string.unit_of_length_km);
        }
        String[] units = unit.split("\\|");
        if (units.length <= type) {
            return "-";
        }
        return units[type];
    }

    public static class FormattedUnitPair{
        private String value;
        private String unit;

        public FormattedUnitPair(String value, String unit){
            this.value = value;
            this.unit = unit;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
