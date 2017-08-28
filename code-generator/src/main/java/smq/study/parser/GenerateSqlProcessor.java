package smq.study.parser;

import smq.study.anno.Column;
import smq.study.anno.SQL;
import smq.study.beans.ColumnBean;
import smq.study.utils.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/8/27.
 */
public class GenerateSqlProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private Types typeUtils;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String separator = "\n";
        String blank = " ";
        /**
         * roundEnv.getRootElements()会返回工程中所有的Class
         */
        //取出每一个用SQL注解的元素
        for (Element e : roundEnv.getElementsAnnotatedWith(SQL.class)) {
            StringBuilder sb = new StringBuilder();
            if (!e.getKind().isClass()) {
                //sql注解只处理类级别
                continue;
            }
            SQL sql = e.getAnnotation(SQL.class);

            /**
             * 表名称
             */
            String table = sql.name();
            if (StringUtils.isEmpty(table)) {
                table = sql.tablePrefix() + StringUtils.lowerCamelCaseWithUnderline(e.getSimpleName().toString());
            }
            sb.append(String.format("DROP TABLE IF EXISTS `%s`;", table));
            sb.append(separator);
            sb.append(String.format("CREATE TABLE `%s` (", table));
            sb.append(separator);

            String columnPrefix = sql.columnPrefix();

            //强转成方法对应的element，同理，如果你的注解是一个类，那你可以强转成TypeElement
            TypeElement te = (TypeElement) e;
            TypeMirror superclass = te.getSuperclass();
            if (superclass instanceof Object) {
                System.out.println("超类是object不需要处理");
            } else {
                //TODO 处理父类
            }

            Set<ColumnBean> columnBeanSet = new TreeSet<>();
            Map<String, ColumnBean> columnBeanMap = new TreeMap();
            //获取被注解类的所有元素
            List<? extends Element> enclosedElements = e.getEnclosedElements();
            for (Element ee : enclosedElements) {
                //处理类的成员变量
                if (ee.getKind() != ElementKind.FIELD) {
                    //否则跳过
                    continue;
                }
                ColumnBean columnBean = new ColumnBean();

                /**
                 * 赋值：默认值
                 */
                //列名称
                columnBean.setName(columnPrefix + StringUtils.lowerCamelCaseWithUnderline(ee.getSimpleName().toString()));
                //类型&长度-自动判断
                TypeMirror typeMirror = ee.asType();
                if (String.class.getName().equalsIgnoreCase(typeMirror.toString())) {
                    columnBean.setType("varchar");
                    columnBean.setLen(45);
                } else if (int.class.getName().equalsIgnoreCase(typeMirror.toString())
                        || Integer.class.getName().equalsIgnoreCase(typeMirror.toString())) {
                    columnBean.setType("int");
                    columnBean.setLen(11);
                } else if (Date.class.getName().equalsIgnoreCase(typeMirror.toString())) {
                    columnBean.setType("datetime");
                }
                //主键：默认false
                //是否为空:默认false
                //默认值
                //注释

                /**
                 * 根据column注解赋值
                 */
                Column column = ee.getAnnotation(Column.class);
                if (column != null) {
                    System.out.println(column.name());
                    //列名称
                    if (StringUtils.isNotEmpty(column.name())) {
                        columnBean.setName(column.name());
                    }
                    //类型
                    if (StringUtils.isNotEmpty(column.type())) {
                        columnBean.setType(column.type());
                    }
                    //长度
                    if (0 != column.len()) {
                        columnBean.setLen(column.len());
                    }

                    //主键
                    if (column.primaryKey()) {
                        columnBean.setPrimaryKey(column.primaryKey());
                    }
                    //是否为空
                    if (column.notNull()) {
                        columnBean.setNotNull(column.notNull());
                    }
                    //默认值
                    if (StringUtils.isNotEmpty(column.defaultValue())) {
                        columnBean.setDefaultValue(column.defaultValue());
                    }
                    //注释
                    if (StringUtils.isNotEmpty(column.comment())) {
                        columnBean.setComment(column.comment());
                    }
                }

                //保存起来
                columnBeanMap.put(columnBean.getName(), columnBean);
                //columnBeanSet.add(columnBean);
            }

            String primaryKey = null;
            for (String key : columnBeanMap.keySet()) {
                ColumnBean bean = columnBeanMap.get(key);
                sb.append("\t");
                sb.append(String.format("`%s`", bean.getName()));
                sb.append(blank);
                if ("datetime".equalsIgnoreCase(bean.getType())) {
                    sb.append(bean.getType());
                } else {
                    sb.append(String.format("%s(%d)", bean.getType(), bean.getLen()));
                }
                sb.append(blank);
                if (bean.isNotNull()) {
                    sb.append("NOT NULL");
                    sb.append(blank);
                }
                if (StringUtils.isNotEmpty(bean.getDefaultValue())) {
                    sb.append(String.format("DEFAULT '%s'", bean.getDefaultValue()));
                    sb.append(blank);
                }
                if (StringUtils.isNotEmpty(bean.getComment())) {
                    sb.append(String.format("COMMENT '%s'", bean.getComment()));
                }

                if (bean.isPrimaryKey()) {
                    primaryKey = bean.getName();
                    sb.append("AUTO_INCREMENT");
                }

                sb.append(",");
                sb.append(separator);
            }

            if (StringUtils.isNotEmpty(primaryKey)) {
                sb.append("\t");
                sb.append(String.format("PRIMARY KEY (`%s`)", primaryKey));
            }


            sb.append(separator);
            sb.append(String.format(") ENGINE=%s DEFAULT CHARSET=%s COMMENT='%s';", sql.dbEngine(), sql.charset(), sql.comment()));

            //System.out.println(sb.toString());
            /*创建文件，写入代码内容*/
            try {
                File dir = new File("auto_sql");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File sqlFile = new File("auto_sql/" + table + ".sql");
                if (sqlFile.exists()) {
                    return false;
                }
                Writer writer = new FileWriter(sqlFile);
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println(sb.toString());
                writer.close();
            } catch (IOException e1) {
                //e1.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new TreeSet<>();
        annotations.add(SQL.class.getName());
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
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        this.typeUtils = processingEnv.getTypeUtils();
    }
}
