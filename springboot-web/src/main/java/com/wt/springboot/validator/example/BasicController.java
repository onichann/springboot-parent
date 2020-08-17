package com.wt.springboot.validator.example;

import com.wt.springboot.pojo.ReturnJson;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @date 2020-03-31 下午 2:05
 * description
 */
@RestController
@RequestMapping("/basic")
@Validated
public class BasicController {


    /**
     * 流程发送/回退
     * @param lcfsVo
     * @return
     */
    @PostMapping("/lcfs")
    public Map<String, Object> lcfs(@RequestBody @Valid LcfsVo lcfsVo) {
        return null;
    }

    /**
     * 保存表单数据
     * @param formDatas
     * @return
     */
    @PostMapping("/save")
    public List<Map<String,Object>> saveTable(@RequestBody @Valid List<FormData> formDatas) throws Exception {
        return null;
    }

    /**
     * 保存表单保存的记录
     */
    @PostMapping("/saveRecord")
    public void saveTableRecord(@RequestBody @Valid TableRecordVo tableRecordVo) {

    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ReturnJson handleResourceNotFoundException(Exception e) {
        StringBuilder strBuilder = new StringBuilder();
        if(e instanceof MethodArgumentNotValidException){
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            if (bindingResult.hasErrors()) {
                for (ObjectError error : bindingResult.getAllErrors()) {
                    strBuilder.append(error.getDefaultMessage()).append(",");
                }
            }
        }else if(e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)e).getConstraintViolations();
            for (ConstraintViolation<?> violation : violations ) {
                strBuilder.append(violation.getMessage()).append(",");
            }
        }
        return new ReturnJson().setCode(HttpStatus.BAD_REQUEST.value()).setData(strBuilder.toString());
    }


}
