package com.springweb.mapper;

import com.springweb.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author boreas
 * @create 2020-02-11 下午 5:02
 */
public interface UserMapper {

    List<UserEntity> getAllXml();

    @Select("SELECT * FROM tb_user")
    @Results({
            @Result(property = "userSex",  column = "user_sex"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name")
    })
    List<UserEntity> getAll();

    @Select("SELECT * FROM tb_user WHERE user_id = #{user_id}")
    @Results({
            @Result(property = "userSex",  column = "user_sex"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name")
    })
    UserEntity getOne(Long userId);

    @Insert("INSERT INTO tb_user(user_name, password, phone) VALUES(#{userName}, #{password}, #{phone})")
    void insert(UserEntity user);

    @Update("UPDATE users SET user_name=#{userName},phone=#{phone} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM tb_user WHERE id =#{id}")
    void delete(Long id);
}
