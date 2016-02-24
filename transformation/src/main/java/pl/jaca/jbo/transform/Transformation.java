package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.Report;
import pl.jaca.jbo.report.Work;
import rx.Observable;
import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class Transformation extends Work {
    private String name;
    private Subject<Report, Report> reportSubject;

    public Transformation(String name) {
        this.name = name;
        this.reportSubject = AsyncSubject.create();
    }

    protected Subject<Report, Report> getReportSubject() {
        return reportSubject;
    }

    @Override
    public Observable<Report> getReports() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public String getWorkName() {
        //TODO
        throw new UnsupportedOperationException();
    }
}
