package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.Reportable;
import pl.jaca.jbo.report.Reporter;
import pl.jaca.jbo.report.Work;
import rx.Observable;
import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class Transformation extends Work implements Reporter {
    private String name;
    private Subject<Reportable, Reportable> reportSubject;

    public Transformation(String name) {
        this.name = name;
        this.reportSubject = AsyncSubject.create();
    }

    public void report(Reportable reportable) {
        reportSubject.onNext(reportable);
    }

    public void reportScheduling(int tasks, String transformation){
        report(new TransformationSchedulingReport(tasks, transformation));
    }

    public void complete() {
        reportSubject.onCompleted();
    }

    public void fail(Throwable e) {
        reportSubject.onError(e);
    }

    @Override
    public Observable<Reportable> getReports() {
        return reportSubject.asObservable();
    }

    @Override
    public String getWorkName() {
        return name;
    }
}
