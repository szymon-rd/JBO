package pl.jaca.jbo.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jaca.jbo.report.Reportable;
import pl.jaca.jbo.report.SchedulingReport;
import pl.jaca.jbo.report.TaskReport;
import pl.jaca.jbo.transform.JavaProject;
import pl.jaca.jbo.transform.Transformation;
import pl.jaca.jbo.transform.optimizers.redundantremover.RedundantMethodRemover;
import pl.jaca.jbo.transform.ProjectJarFile;
import rx.Observer;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Jaca777
 *         Created 2016-02-27 at 10
 */
public class Launcher {
    private static Logger log = LoggerFactory.getLogger("JBO");
    private static Scanner input = new Scanner(System.in);

    public static void main(String... args) throws IOException {
        log.info("Launcher is running: ");
        log.info("Enter project path: ");
        String projectPath = input.next();
        RedundantMethodRemover r = new RedundantMethodRemover();
        JavaProject project = new ProjectJarFile(projectPath);
        Transformation t = r.transform(project);
        t.getReports().subscribe(new ConsoleOutput());
    }

    private static class ConsoleOutput implements Observer<Reportable> {

        Map<String, Work> works = Collections.synchronizedMap(new HashMap<>());

        @Override
        public void onCompleted() {
            log.info("Transformation complete!");
        }

        @Override
        public void onError(Throwable e) {
            log.error("Error was thrown by the transformer.", e);
        }

        @Override
        public void onNext(Reportable reportable) {
            switch (reportable.getTag()) {
                case SchedulingReport.TAG:
                    SchedulingReport schedulingReport = (SchedulingReport) reportable;
                    log.info("Scheduled new task: " + schedulingReport.getTaskName());
                    int tasks = schedulingReport.getTaskCount();
                    works.put(schedulingReport.getTaskName(), new Work(tasks));
                    break;
                case TaskReport.TAG:
                    TaskReport taskReport = (TaskReport) reportable;
                    Work work = works.get(taskReport.getTaskName());
                    work.completeTask();
                    int percent = (int) (work.getPercentComplete() * 100);
                    log.info("[" + percent + " %]  -  Task completed: " + taskReport.getTaskName() + " (" + taskReport.getTarget() + ")");
                    if (work.getTasksLeft() == 0) {
                        log.info("Work completed: " + taskReport.getTaskName());
                        works.remove(taskReport.getTaskName());
                    }
                    break;
            }
        }
    }

    private static class Work {
        private final int allTasks;
        private int tasksLeft;

        public Work(int allTasks) {
            this.allTasks = allTasks;
            this.tasksLeft = allTasks;
        }

        void completeTask() {
            tasksLeft--;
        }

        float getPercentComplete() {
            return 1 - tasksLeft / (float) allTasks;
        }

        int getTasksLeft() {
            return tasksLeft;
        }
    }
}
