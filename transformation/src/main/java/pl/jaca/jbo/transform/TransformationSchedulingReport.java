package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.SchedulingReport;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class TransformationSchedulingReport extends SchedulingReport {
    private int tasks;
    private String transfromation;

    public TransformationSchedulingReport(int tasks, String transformation) {
        this.tasks = tasks;
        this.transfromation = transformation;
    }

    @Override
    public String getMessage() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTaskCount() {
        //TODO
        throw new UnsupportedOperationException();
    }
}
