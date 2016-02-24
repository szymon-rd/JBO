package pl.jaca.jbo.transform.optimizers.redundantremover;


import pl.jaca.jbo.transform.JavaProject;
import pl.jaca.jbo.report.Work;
import pl.jaca.jbo.transform.Transformation;
import pl.jaca.jbo.transform.Transformer;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public class RedundantMethodRemover implements Transformer {
    @Override
    public Transformation transform(JavaProject project) {
        Transformation transformation = new Transformation("Redundant methods removal.");
        return transformation;
    }
}
