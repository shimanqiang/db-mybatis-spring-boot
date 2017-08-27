package smq.study.parser;

import smq.study.anno.Column;
import smq.study.anno.PrimaryKey;
import smq.study.anno.SQL;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/8/27.
 */
public class GenerateSqlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("generate sql script");
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new TreeSet<>();
        annotations.add(SQL.class.getName());
        annotations.add(PrimaryKey.class.getName());
        annotations.add(Column.class.getName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }
}
