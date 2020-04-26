package snoopy.task.validator;

import org.apache.commons.lang.StringUtils;
import snoopy.task.model.TaskModel;

public class ValidateUtil {

    public static boolean inputValidator(TaskModel taskModel){
        if (StringUtils.isEmpty(taskModel.getClassName())) {
            return false;
        }
        return true;
    }

}
