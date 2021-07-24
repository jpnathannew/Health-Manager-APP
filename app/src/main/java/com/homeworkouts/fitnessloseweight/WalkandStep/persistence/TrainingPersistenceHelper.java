
package com.homeworkouts.fitnessloseweight.WalkandStep.persistence;

import android.content.Context;

import com.homeworkouts.fitnessloseweight.WalkandStep.models.Training;

import java.util.List;



public class TrainingPersistenceHelper {
    public static final String LOG_CLASS = TrainingPersistenceHelper.class.getName();


    public static List<Training> getAllItems(Context context) {
        return new TrainingDbHelper(context).getAllTrainings();
    }

    public static Training getItem(long id, Context context) {
        return new TrainingDbHelper(context).getTraining((int) id);
    }


    public static Training getActiveItem(Context context) {
        return new TrainingDbHelper(context).getActiveTraining();
    }


    public static Training save(Training item, Context context) {
        if (item == null) {
            return null;
        }
        if (item.getId() <= 0) {
            long insertedId = insert(item, context);
            if (insertedId == -1) {
                return null;
            } else {
                item.setId(insertedId);
                return item;
            }
        } else {
            int affectedRows = update(item, context);
            if (affectedRows == 0) {
                return null;
            } else {
                return item;
            }
        }
    }


    public static boolean delete(Training item, Context context) {
        new TrainingDbHelper(context).deleteTraining(item);
        return true;
    }


    protected static long insert(Training item, Context context) {
        return new TrainingDbHelper(context).addTraining(item);
    }


    protected static int update(Training item, Context context) {
        return new TrainingDbHelper(context).updateTraining(item);
    }
}
