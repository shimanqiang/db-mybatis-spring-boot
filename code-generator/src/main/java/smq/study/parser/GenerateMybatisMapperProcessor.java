package smq.study.parser;

import smq.study.anno.GenerateMybatisMapper;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.TreeSet;

public class GenerateMybatisMapperProcessor extends AbstractProcessor {
    private Filer filter;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filter = processingEnv.getFiler();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(1111);
        messager.printMessage(Diagnostic.Kind.WARNING,"==========smq===========");
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //return super.getSupportedAnnotationTypes();
        Set<String> supporedAnnoSet = new TreeSet<>();
        supporedAnnoSet.add(GenerateMybatisMapper.class.getName());
        return supporedAnnoSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //return super.getSupportedSourceVersion();
        return SourceVersion.latestSupported();
    }

}
