## 配置编译期注解

* maven配置

```

<plugin>
    <!--http://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html-->
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <proc>none</proc>
        <annotationProcessors>
            <annotationProcessor>smq.study.parser.GenerateMybatisMapperProcessor</annotationProcessor>
        </annotationProcessors>
    </configuration>
</plugin>
```

* Gradle配置
```
configurations {
    annotationProcessor
}

task configureAnnotationProcessing(type: JavaCompile, group: 'build', description: 'Processes the @Configuration classes') {
    source = sourceSets.main.java
    classpath = configurations.compile + configurations.annotationProcessor
    options.compilerArgs = [
            "-proc:none",
            "-processor", "com.huifenqi.jedi.study.anno.GenerateSQLMapperProcessor"
    ]
    destinationDir = buildDir
}

compileJava {
    dependsOn configureAnnotationProcessing
}

```