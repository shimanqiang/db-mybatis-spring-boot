package smq.study.utils;

/**
 * Created by Administrator on 2017/8/27.
 */
public class StringUtils {

    public static String lowerCamelCaseWithUnderline(String name) {
        return StringUtils.separateCamelCase(name, "_").toLowerCase();
    }

    public static String separateCamelCase(String name, String separator) {
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0) {
                translation.append(separator);
            }
            translation.append(character);
        }
        return translation.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }
}
