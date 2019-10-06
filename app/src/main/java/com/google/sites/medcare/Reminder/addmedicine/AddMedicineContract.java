package com.google.sites.medcare.Reminder.addmedicine;

import com.google.sites.medcare.Reminder.BasePresenter;
import com.google.sites.medcare.Reminder.BaseView;
import com.google.sites.medcare.Reminder.source.MedicineAlarm;
import com.google.sites.medcare.Reminder.source.Pills;

import java.util.List;

public interface AddMedicineContract {

    interface View extends BaseView<Presenter> {

        void showEmptyMedicineError();

        void showMedicineList();

        boolean isActive();

    }

    interface  Presenter extends BasePresenter {


        void saveMedicine(MedicineAlarm alarm, Pills pills);


        boolean isDataMissing();

        boolean isMedicineExits(String pillName);

        long addPills(Pills pills);

        Pills getPillsByName(String pillName);

        List<MedicineAlarm> getMedicineByPillName(String pillName);

        List<Long> tempIds();

        void deleteMedicineAlarm(long alarmId);

    }
}
