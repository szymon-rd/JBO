package pl.jaca.jbo.report;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public abstract class SchedulingReport implements Reportable {

    public static final String TAG = "sch";

    @Override
    public String getTag() {
        return TAG;
    }

    public abstract int getTaskCount();
}
