package com.springweb.repository;

import com.springweb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author boreas
 * @create 2020-02-05 下午 2:57
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    User findByUserNameOrEmail(String username, String email);

    Long countByUserName(String userName);

    @Transactional(timeout = 10)
    @Modifying
    @Query("update User set userName = ?1 where id = ?2")
    int modifyById(String  userName, Long id);

    @Transactional
    @Modifying// 涉及到修改、删除则需要再加上@Modifying注解
    @Query("delete from User where id = ?1")
    void deleteById(Long id);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);
    // nativeQuery标志设置为true来执行原生查询
    @Query(value = "select * from tb_user u where u.email = ?1", nativeQuery = true)
    User queryByEmail(String email);

    @Query("select u from User u where u.userName = :username or u.email = :email")
    User getByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Query("select u from User u")
    Page<User> findALL(Pageable pageable);

    Page<User> findByUserName(String userName, Pageable pageable);

    Slice<User> findByUserNameAndEmail(String userName, String email, Pageable pageable);
}