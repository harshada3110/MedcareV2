package com.google.sites.medcare.Reminder.medicine;

import com.google.sites.medcare.Reminder.BasePresenter;
import com.google.sites.medcare.Reminder.BaseView;
import com.google.sites.medcare.Reminder.source.MedicineAlarm;

import java.util.List;

/**
 * Created by gautam on 13/07/17.
 */

public interface MedicineContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showMedicineList(List<MedicineAlarm> medicineAlarmList);

        void showAddMedicine();

        void showMedicineDetails(long medId, String medName);

        void showLoadingMedicineError();

        void showNoMedicine();

        void showSuccessfullySavedMessage();

        boolean isActive();


    }

    interface Presenter extends BasePresenter {

        void onStart(int day);

        void reload(int day);

        void result(int requestCode, int resultCode);

        void loadMedicinesByDay(int day, boolean showIndicator);


        void addNewMedicine();

    }
}
