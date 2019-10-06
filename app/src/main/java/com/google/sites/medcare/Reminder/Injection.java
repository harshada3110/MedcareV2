package com.google.sites.medcare.Reminder;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.sites.medcare.Reminder.source.MedicineRepository;
import com.google.sites.medcare.Reminder.source.local.MedicinesLocalDataSource;


/**
 * Created by gautam on 13/07/17.
 */

public class Injection {

    public static MedicineRepository provideMedicineRepository(@NonNull Context context) {
        return MedicineRepository.getInstance(MedicinesLocalDataSource.getInstance(context));
    }
}
