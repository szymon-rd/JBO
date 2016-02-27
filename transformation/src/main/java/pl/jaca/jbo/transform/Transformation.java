package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.Reportable;
import pl.jaca.jbo.report.Reporter;
import pl.jaca.jbo.report.Work;
import pl.jaca.jbo.util.FutureUtil;
import rx.Observable;
import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Jaca777
 *         Created 2016-02-24 at 22
 */
public class Transformation extends Work implements Reporter {
    private String name;
    private Subject<Reportable, Reportable> reportSubject = AsyncSubject.create();
    private CompletableFuture<JavaProject> result = new CompletableFuture<>();

    public Transformation(String name) {
        this.name = name;
    }

    @Override
    public void report(Reportable reportable) {
        reportSubject.onNext(reportable);
    }

    public void reportScheduling(int tasks, String transformation) {
        report(new TransformationSchedulingReport(tasks, transformation));
    }

    @Override
    public void fail(Throwable e) {
        reportSubject.onError(e);
    }

    public void complete(JavaProject resultProject) {
        result.complete(resultProject);
        reportSubject.onCompleted();
    }

    @Override
    public Observable<Reportable> getReports() {
        return reportSubject.asObservable();
    }

    @Override
    public String getWorkName() {
        return name;
    }

    public Future<JavaProject> getResult() {
        return result;
    }
}
