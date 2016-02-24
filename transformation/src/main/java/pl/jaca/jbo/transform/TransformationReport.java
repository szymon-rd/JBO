package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.TaskReport;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class TransformationReport extends TaskReport {
    private String target, transformation;

    public TransformationReport(String target, String transformation) {
        this.target = target;
        this.transformation = transformation;
    }

    @Override
    public String getMessage() {
        return "[" + target + "] " + transformation;
    }
}
