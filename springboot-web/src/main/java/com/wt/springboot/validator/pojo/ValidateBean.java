package com.wt.springboot.validator.pojo;


import com.wt.springboot.validator.annotation.ListNotHasNullField;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.util.List;

@Component
//@Scope("prototype")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Scope(WebApplicationContext.)
public class ValidateBean {

    public interface update{}

    public interface add{}

    @NotNull(message = "id不为空",groups = update.class)
    @Null(message = "id不为空",groups = add.class)
    private String id;

    @Size(min = 3,max = 20,message = "name 3-20长度")
    @NotNull
    private String name;

    @Range(min = 1,max = 10,message = "age 1-10之间")
    private int age;

    /**
     * 书名
     */
    @NotEmpty(message = "书名不能为空")
    private String bookName;
    /**
     * ISBN号
     */
    @NotNull(message = "ISBN号不能为空")
    private String bookIsbn;
    /**
     * 单价
     */
    @DecimalMin(value = "0.1",message = "单价最低为0.1")
    private double price;

    @ListNotHasNullField(message = "List集合中不能含有null元素鸭")
    private List<String> stringList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
