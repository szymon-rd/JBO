package pl.jaca.jbo.report;


import rx.Observable;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public abstract class Work {
    public abstract Observable<Report> getReports();
    public abstract String getWorkName();
}
