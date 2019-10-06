package com.google.sites.medcare.Reminder.alarm;

import com.google.sites.medcare.Reminder.BasePresenter;
import com.google.sites.medcare.Reminder.BaseView;
import com.google.sites.medcare.Reminder.source.History;
import com.google.sites.medcare.Reminder.source.MedicineAlarm;

/**
 * Created by gautam on 13/07/17.
 */

public interface ReminderContract {

    interface View extends BaseView<Presenter> {

        void showMedicine(MedicineAlarm medicineAlarm);

        void showNoData();

        boolean isActive();

        void onFinish();

    }

    interface Presenter extends BasePresenter {

        void finishActivity();

        void onStart(long id);

        void loadMedicineById(long id);

        void addPillsToHistory(History history);

    }
}
