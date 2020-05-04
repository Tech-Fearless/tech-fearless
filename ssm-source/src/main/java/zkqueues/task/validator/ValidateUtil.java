package zkqueues.task.validator;

import org.apache.commons.lang.StringUtils;
import zkqueues.task.model.TaskModel;

public class ValidateUtil {

    public static boolean inputValidator(TaskModel taskModel){
        if (StringUtils.isEmpty(taskModel.getClassName())) {
            return false;
        }
        return true;
    }

}
