package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.SchedulingReport;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class TransformationSchedulingReport extends SchedulingReport {
    private int tasks;
    private String transformation;

    public TransformationSchedulingReport(int tasks, String transformation) {
        this.tasks = tasks;
        this.transformation = transformation;
    }

    @Override
    public int getTaskCount() {
        return tasks;
    }

    @Override
    public String getTaskName() {
        return transformation;
    }
}
