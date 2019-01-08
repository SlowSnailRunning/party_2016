package cn.edu.cdcas.partyschool.util;

/**
 * the public class for holding static resources.
 *
 * @author Char Jin
 * @date 2019-01-07
 */
public class R {

    public static final class column_header {
        // the column header displayed in the first row of student sheet of excel exported.
        public static final String[] COLUMN_HEADER_STUDENT = new String[]{"序号", "年级",
                "学院", "专业", "姓名", "学号", "党校号"};

        // the column header displayed in the first row of question sheet of excel exported.
        public static final String[] COLUMN_HEADER_QUESTION = new String[]{"题干(intro)", "A选项(option_a)",
                "B选项(option_b)", "C选项(option_c)", "D选项(option_d)", "答案(result)", "试题类型(type)"};
    }

    public static final class user_type {
        public static final String STUDENT = "student";
        public static final String MANAGER = "manager";

    }

    public static final class question_type {
        public static final int RADIO = 1;
        public static final int CHECK = 2;
        public static final int JUDGE = 3;
        public static final int FILL_BLANK = 4;
        public static final int SAQ = 5;
    }

    public static final class student_exam_state {
        public static final int NO_STARTING = 0;
        public static final int PASSING_FIRST_EXAM = 1;
        public static final int NO_MAKE_UP = 2;
        public static final int NOT_PASSING_MAKE_UP = 3;
        public static final int PASSING_MAKE_UP = 4;

    }

}
