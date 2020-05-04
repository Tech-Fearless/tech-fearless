package zkqueues.log;

import org.apache.commons.lang.StringUtils;
import zkqueues.base.config.SnoopyConfig;
import zkqueues.util.DateUtil;

public class SnoopyLogger {

    protected static final String SNOOPY_LOG_OUTPUT_URL = SnoopyConfig.getConfigByKey("snoopy.log.output.class");
    protected static final String SNOOPY_SHOW_LOG = SnoopyConfig.getConfigByKey("snoopy.log.show");

    protected static BaseLogger logger;
    protected static Boolean isShowLog;

    static {

    }

    private static void loadLogClass(){
        if (StringUtils.isEmpty(SNOOPY_LOG_OUTPUT_URL)) {
            logger = new BaseLogger();
            return;
        }

        try {
            Class<?> logClass = SnoopyLogger.class.getClassLoader().loadClass(SNOOPY_LOG_OUTPUT_URL);
            logger = (BaseLogger)logClass.newInstance();
        }catch (Exception e){
            logger = new BaseLogger();
            e.printStackTrace();
        }

    }

    private static void loadIsShowLog(){
        if (StringUtils.isEmpty(SNOOPY_SHOW_LOG)){
            isShowLog = true;
            return;
        }
        try {
            isShowLog = Boolean.parseBoolean(SNOOPY_SHOW_LOG);
        }catch (Exception e){
            isShowLog = true;
            e.printStackTrace();
        }
    }

    public static void info(String message){
        if (!isShowLog) {
            return;
        }

        if (logger.getClass().isAssignableFrom(BaseLogger.class)){
            logger.showInfoLog("[" + DateUtil.getNowDateSecondFromString() + "]" +
                    " [*****Info Log*****] " + message);
        }
    }

    public static void error(Class<?> clazz, String methodName, String message, Throwable th){
        String errStr = getErrorMsgStr(clazz, methodName, message, th);
        if (logger.getClass().isAssignableFrom(BaseLogger.class)){
            logger.showErrorLog("[" + DateUtil.getNowDateSecondFromString() + "]"
            + " [***** Error Log *****]" + errStr);
            return;
        }
        logger.showErrorLog(" [***** Error Log *****]" + errStr);
    }

    private static String getErrorMsgStr(Class<?> clazz, String methodName, String message, Throwable th){
        StringBuilder errStr = new StringBuilder();
        errStr.append("\n[class:" + clazz.getName() + "] ");
        errStr.append("\n[method:" + methodName + "] ");
        errStr.append("\n[message:" + message + "] ");
        errStr.append("\n[exception:" + th.toString() + "]\n");
        return errStr.toString();
    }
}
