package ir.hosseinrasti.app.jobjo.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Hossein on 7/13/2018.
 */

public interface SchedulerProvider {


    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
