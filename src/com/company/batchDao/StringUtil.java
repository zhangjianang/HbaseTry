package com.company.batchDao;


import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final String EMOJI_REGEX = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[☀-⟿]";

    public StringUtil() {
    }

    public static String clearLeftRightSpace(String str) {
        if(str == null) {
            return null;
        } else if(str.trim().length() == 0) {
            return "";
        } else {
            int start = 0;
            boolean isAllSpace = false;

            int end;
            for(end = 0; end < str.length(); ++end) {
                if(str.charAt(end) != 32 && str.charAt(end) != 12288) {
                    start = end;
                    isAllSpace = true;
                    break;
                }
            }

            if(!isAllSpace) {
                return "";
            } else {
                end = str.length();

                for(int i = str.length() - 1; i >= 0; --i) {
                    if(str.charAt(i) != 32 && str.charAt(i) != 12288) {
                        end = i + 1;
                        break;
                    }
                }

                str = str.substring(start, end);
                return str;
            }
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmail(String email) {
        return isNotEmpty(email) && email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }

    public static boolean isMobilePhone(String phone) {
        return phone.matches("1[3|4|5|7|8][\\d]{9}");
    }

    public static boolean isCaptcha(String captcha) {
        return captcha.matches("/^[0-9]{6}$/");
    }

    public static boolean isUserName(String name) {
        return name != null && name.length() >= 1 && name.length() <= 24 && name.matches("[0-9a-zA-Z\\u4e00-\\u9fa5_-]+");
    }

    public static String encryptPassport(String passport) {
        String prefix;
        String suffix;
        if(isEmail(passport)) {
            prefix = passport.split("@")[0];
            if(prefix.length() > 3) {
                suffix = passport.split("@")[1];
                prefix = prefix.substring(0, prefix.length() - 3) + "***";
                passport = prefix + "@" + suffix;
            }
        } else if(isMobilePhone(passport)) {
            prefix = passport.substring(0, 3);
            suffix = passport.substring(7, 11);
            passport = prefix + "****" + suffix;
        }

        return passport;
    }

    public static boolean isPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 16 && password.matches("[a-zA-Z0-9\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b\\uFF01\\u201c\\u201d\\u2018\\u2019\\u300e\\u300f\\u300c\\u300d\\uFF09\\uFF08\\.\\_\\-\\?\\~\\!\\@\\#\\$\\%\\^\\&\\*\\\\\\+\\`\\=\\[\\]\\(\\)\\{\\}\\|\\;\\\'\\:\\\"\\,\\/\\<\\>]+");
    }

    public static <T> String splitList(List<T> list) {
        if(list != null && !list.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object str = i$.next();
                buffer.append(str).append(',');
            }

            return buffer.substring(0, buffer.length() - 1);
        } else {
            return null;
        }
    }

    public static String removeEmoji(String str) {
        Pattern emojiPattert = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[☀-⟿]", 66);
        Matcher emojiMatcher = emojiPattert.matcher(str);
        String rlt = emojiMatcher.replaceAll("");
        return rlt;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
